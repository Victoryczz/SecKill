package seu.vczz.seckill.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import seu.vczz.seckill.common.CodeMsg;
import seu.vczz.seckill.common.ServerResponse;
import seu.vczz.seckill.domain.SKOrder;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.rabbitmq.MQSender;
import seu.vczz.seckill.rabbitmq.MiaoShaMessage;
import seu.vczz.seckill.redis.RedisService;
import seu.vczz.seckill.redis.keyprefix.GoodsKey;
import seu.vczz.seckill.service.IGoodsService;
import seu.vczz.seckill.service.IMiaoShaService;
import seu.vczz.seckill.service.IOrderService;
import seu.vczz.seckill.vo.SKGoodsVo;

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
    @RequestMapping(value = "/do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<CodeMsg> miaosha(User user, @RequestParam("goodsId") Long goodsId){
        //////////////////////////////////////////////////////////开始进行优化
        if (user == null){
            return ServerResponse.error(CodeMsg.SESSION_ERROR);
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
}
