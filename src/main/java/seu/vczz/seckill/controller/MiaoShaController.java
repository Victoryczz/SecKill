package seu.vczz.seckill.controller;

import com.mysql.fabric.Server;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import seu.vczz.seckill.common.CodeMsg;
import seu.vczz.seckill.common.ServerResponse;
import seu.vczz.seckill.domain.SKOrder;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.rabbitmq.MQSender;
import seu.vczz.seckill.rabbitmq.MiaoShaMessage;
import seu.vczz.seckill.redis.RedisService;
import seu.vczz.seckill.redis.keyprefix.GoodsKey;
import seu.vczz.seckill.redis.keyprefix.MiaoShaKey;
import seu.vczz.seckill.service.IGoodsService;
import seu.vczz.seckill.service.IMiaoShaService;
import seu.vczz.seckill.service.IOrderService;
import seu.vczz.seckill.util.MD5Util;
import seu.vczz.seckill.util.UUIDUtil;
import seu.vczz.seckill.vo.SKGoodsVo;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;


/**
 * CREATE by vczz on 2018/5/14
 * 秒杀Controller
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoShaController implements InitializingBean{

    @Autowired
    private IGoodsService iGoodsService;
    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IMiaoShaService iMiaoShaService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private MQSender mqSender;
    //内存标记，标记哪些商品已经被秒杀
    private HashMap<Long, Boolean> localOverMap = new HashMap<>();
    /**
     * 秒杀
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/{path}/do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<CodeMsg> miaosha(User user, @RequestParam("goodsId") Long goodsId,@PathVariable("path")String path){
        //////////////////////////////////////////////////////////开始进行优化
        if (user == null || path == null || goodsId == null){
            return ServerResponse.error(CodeMsg.ILLEGAL_REQUEST);
        }
        //验证path
        String redisPath = redisService.get(MiaoShaKey.MIAOSHA_PATH, ""+user.getId()+"_"+goodsId, String.class);
        if (!StringUtils.equals(redisPath, path)){
            return ServerResponse.error(CodeMsg.PATH_NOT_EXISTS);
        }
        //1.初始化时候加载库存到redis，使改类实现InitializingBean接口
        //2.预减库存。但是不能每次都从redis预减库存，因此先判断本地,如果没了，redis都不需要访问了
        boolean isOver = localOverMap.get(goodsId);
        if (isOver){
            //如果商品没了，直接返回over
            return ServerResponse.error(CodeMsg.MIAO_SHA_OVER);
        }
        //先判断库存
        long stock = redisService.decr(GoodsKey.GOODS_STOCK, ""+goodsId);
        if (stock < 0){
            //先将本地库存设置为0
            localOverMap.put(goodsId, true);
            return ServerResponse.error(CodeMsg.MIAO_SHA_OVER);
        }
        //再判断是否秒杀过
        SKOrder skOrder = iOrderService.getSKOrderByUIdAndGoodsId(user.getId(), goodsId);
        if (skOrder != null){
            //已经秒杀过了，则吧增加的库存加回来
            redisService.incr(GoodsKey.GOODS_STOCK, ""+goodsId);
            return ServerResponse.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //3.既没有秒杀过，也有库存，就发消息,在收到消息之后进行下订单的处理
        MiaoShaMessage miaoShaMessage = new MiaoShaMessage();
        miaoShaMessage.setUser(user);
        miaoShaMessage.setGoodsId(goodsId);
        mqSender.sendSecKillMessage(miaoShaMessage);
        //返回一个排队中
        return ServerResponse.success(CodeMsg.ON_LINE);
    }
    /**
     * InitializationBean的方法实现
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //先将产品数量加载到redis
        List<SKGoodsVo> skGoodVos = iGoodsService.listSKGoodsVo();
        if (skGoodVos == null){
            return;
        }
        for (SKGoodsVo skGoodsVo : skGoodVos){
            redisService.set(GoodsKey.GOODS_STOCK, ""+ skGoodsVo.getId(), skGoodsVo.getStockCount());
            localOverMap.put(skGoodsVo.getId(), false);
        }
    }
    /**
     * 供前台轮询，获取秒杀结果
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/result")
    @ResponseBody
    public ServerResponse<Long> miaoShaResult(User user, @RequestParam("goodsId")Long goodsId){
        if (user == null){
            return ServerResponse.error(CodeMsg.SESSION_ERROR);
        }
        long result = iMiaoShaService.getMiaoShaResult(user.getId(), goodsId);
        return ServerResponse.success(result);
    }

    /**
     * 请求秒杀的地址，前端点击了秒杀，先去请求秒杀地址，然后转发到秒杀接口，而不能直接暴露秒杀接口
     * @param user
     * @param goodsId
     * @param verifyCode 验证码
     * @return
     */
    @RequestMapping("/path")
    @ResponseBody
    public ServerResponse<String> getMiaoShaPath(User user,@RequestParam("goodsId") Long goodsId,
                                                 @RequestParam("verifyCode")Integer verifyCode){
        if (user == null || goodsId == null || verifyCode == null){
            return ServerResponse.error(CodeMsg.SESSION_ERROR);
        }
        //验证验证码
        boolean check = iMiaoShaService.checkVerifyCode(user, goodsId, verifyCode);
        if (!check){
            return ServerResponse.error(CodeMsg.VERIFY_CODE_WRONG);
        }
        //随机数加密
        String str = MD5Util.md5(UUIDUtil.uuid());
        //放到redis,每个用户对每件商品都有自己的秒杀地址
        redisService.set(MiaoShaKey.MIAOSHA_PATH, ""+user.getId()+"_"+goodsId, str);
        return ServerResponse.success(str);
    }

    /**
     * 请求验证码
     * @param response
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value="/verifyCode", method=RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> getMiaoshaVerifyCod(HttpServletResponse response, User user,
                                              @RequestParam("goodsId")Long goodsId) {
        if(user == null || goodsId == null) {
            return ServerResponse.error(CodeMsg.ILLEGAL_REQUEST);
        }
        try {
            BufferedImage image  = iMiaoShaService.createVerifyCode(user, goodsId);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return null;
        }catch(Exception e) {
            e.printStackTrace();
            return ServerResponse.error(CodeMsg.MIAOSHA_FAILED);
        }
    }
}
