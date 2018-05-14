package seu.vczz.seckill.service;

import seu.vczz.seckill.domain.Order;
import seu.vczz.seckill.domain.SKOrder;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.vo.SKGoodsVo;

/**
 * CREATE by vczz on 2018/5/14
 */
public interface IOrderService {


    /**
     * 根据用户ID和商品ID获取秒杀订单
     * @param userId
     * @param goodsId
     * @return
     */
    SKOrder getSKOrderByUIdAndGoodsId(Long userId, Long goodsId);

    /**
     * 创建订单
     * @param user
     * @param skGoodsVo
     * @return
     */
    Order createOrder(User user, SKGoodsVo skGoodsVo);

}
