package seu.vczz.seckill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seu.vczz.seckill.dao.OrderDao;
import seu.vczz.seckill.domain.Order;
import seu.vczz.seckill.domain.SKOrder;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.redis.RedisService;
import seu.vczz.seckill.redis.keyprefix.OrderKey;
import seu.vczz.seckill.service.IOrderService;
import seu.vczz.seckill.vo.SKGoodsVo;
import java.util.Date;

/**
 * CREATE by vczz on 2018/5/14
 * 订单service
 */
@Service("iOrderService")
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private RedisService redisService;
    /**
     * 根据用户ID和商品ID获取秒杀订单
     * @param userId
     * @param goodsId
     * @return
     */
    public SKOrder getSKOrderByUIdAndGoodsId(Long userId, Long goodsId){

        return redisService.get(OrderKey.GET_ORDER_BY_USER_GOODS_ID, ""+userId+"_"+goodsId, SKOrder.class);
    }
    /**
     * 创建订单1.普通订单 2.秒杀订单
     * @param user
     * @param skGoodsVo
     * @return
     */
    @Transactional
    public Order createOrder(User user, SKGoodsVo skGoodsVo){
        //插入普通订单
        Order order = new Order();
        order.setCreateDate(new Date());
        order.setDeliveryAddrId(0L);
        order.setGoodsCount(1);
        order.setGoodsId(skGoodsVo.getId());
        order.setGoodsName(skGoodsVo.getGoodsName());
        order.setGoodsPrice(skGoodsVo.getMiaoshaPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setUserId(user.getId());
        //注意：此时已经将自增主键ID绑定到了对象上
        orderDao.insertOrder(order);
        //插入秒杀订单
        SKOrder skOrder = new SKOrder();
        skOrder.setGoodsId(skGoodsVo.getId());
        skOrder.setOrderId(order.getId());
        skOrder.setUserId(user.getId());
        orderDao.insertSKOrder(skOrder);

        //再将订单写入redis缓存
        redisService.set(OrderKey.GET_ORDER_BY_USER_GOODS_ID, ""+user.getId()+"_"+skGoodsVo.getId(), skOrder);
        return order;
    }

    /**
     * 根据订单号获得订单信息
     * @param orderId
     * @return
     */
    public Order getOrderByOrderId(Long orderId){
        return orderDao.getOrderByOrderId(orderId);
    }
}
