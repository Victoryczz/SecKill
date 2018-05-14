package seu.vczz.seckill.service;

import seu.vczz.seckill.domain.Order;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.vo.SKGoodsVo;

/**
 * CREATE by vczz on 2018/5/14
 * 秒杀service
 */
public interface IMiaoShaService {

    /**
     * 秒杀，1.减少库存 2.创建订单
     * 是个事务
     * @param user
     * @param skGoodsVo
     * @return
     */
    Order miaosha(User user, SKGoodsVo skGoodsVo);


}
