package seu.vczz.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import seu.vczz.seckill.common.CodeMsg;
import seu.vczz.seckill.common.ServerResponse;
import seu.vczz.seckill.domain.Order;
import seu.vczz.seckill.domain.SKOrder;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.service.IGoodsService;
import seu.vczz.seckill.service.IMiaoShaService;
import seu.vczz.seckill.service.IOrderService;
import seu.vczz.seckill.vo.SKGoodsVo;


/**
 * CREATE by vczz on 2018/5/14
 * 秒杀Controller
 */
@Controller
@RequestMapping("/miaosha")
public class SeckillController {

    @Autowired
    private IGoodsService iGoodsService;
    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IMiaoShaService iMiaoShaService;

    /**
     * 秒杀
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Order> miaosha(User user, @RequestParam("goodsId") Long goodsId){
        //如果用户不存在，则跳转到登录页面
        if (user == null){
            return ServerResponse.error(CodeMsg.SESSION_ERROR);
        }
        //判断库存
        //注意：这一步可能就买超了
        SKGoodsVo skGoodsVo = iGoodsService.getSKGoodsByGoodsId(goodsId);
        int stock = skGoodsVo.getStockCount();
        if (stock <= 0){
            //如果没有库存了
            return ServerResponse.error(CodeMsg.STOCK_NOT_ENOUGH);
        }
        //有库存
        //判断有没有秒杀成功，一个用户只能购买一件
        SKOrder skOrder = iOrderService.getSKOrderByUIdAndGoodsId(user.getId(), goodsId);
        if (skOrder != null){
            return ServerResponse.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //否则秒杀成功
        Order order = iMiaoShaService.miaosha(user, skGoodsVo);
        //返回订单详情页
        return ServerResponse.success(order);
    }
}
