package seu.vczz.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import seu.vczz.seckill.common.CodeMsg;
import seu.vczz.seckill.common.ServerResponse;
import seu.vczz.seckill.domain.Order;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.service.IGoodsService;
import seu.vczz.seckill.service.IOrderService;
import seu.vczz.seckill.vo.GoodsDetailVo;
import seu.vczz.seckill.vo.OrderDetailVo;
import seu.vczz.seckill.vo.SKGoodsVo;

/**
 * CREATE by vczz on 2018/5/15
 * 订单
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IGoodsService iGoodsService;

    /**
     * 获取订单详情
     * @param user
     * @param orderId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ServerResponse<OrderDetailVo> detail(User user,@RequestParam("orderId") Long orderId){
        if (user == null){
            return ServerResponse.error(CodeMsg.SESSION_ERROR);
        }
        //查询订单
        Order order = iOrderService.getOrderByOrderId(orderId);
        if (order == null){
            return ServerResponse.error(CodeMsg.ORDER_NOT_EXISTS);
        }
        long goodsId = order.getGoodsId();
        //在通过商品Id拿到秒杀的商品
        SKGoodsVo goods = iGoodsService.getSKGoodsByGoodsId(goodsId);
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setGoods(goods);
        orderDetailVo.setOrder(order);
        return ServerResponse.success(orderDetailVo);
    }

}
