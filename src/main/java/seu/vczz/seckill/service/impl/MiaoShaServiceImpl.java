package seu.vczz.seckill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seu.vczz.seckill.domain.Order;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.service.IGoodsService;
import seu.vczz.seckill.service.IMiaoShaService;
import seu.vczz.seckill.service.IOrderService;
import seu.vczz.seckill.vo.SKGoodsVo;

/**
 * CREATE by vczz on 2018/5/14
 */
@Service("iMiaoShaService")
public class MiaoShaServiceImpl implements IMiaoShaService {

    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IGoodsService iGoodsService;

    /**
     * 秒杀，1.减少库存 2.创建订单
     * 是个事务
     * @param user
     * @param skGoodsVo
     * @return
     */
    @Transactional
    public Order miaosha(User user, SKGoodsVo skGoodsVo){
        //减少库存
        iGoodsService.reduceStock(skGoodsVo);
        //创建订单，普通订单以及秒杀订单两个
        return iOrderService.createOrder(user, skGoodsVo);
    }

}
