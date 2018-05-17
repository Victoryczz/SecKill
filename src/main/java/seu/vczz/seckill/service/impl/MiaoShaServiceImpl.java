package seu.vczz.seckill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seu.vczz.seckill.domain.Order;
import seu.vczz.seckill.domain.SKOrder;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.redis.RedisService;
import seu.vczz.seckill.redis.keyprefix.MiaoShaKey;
import seu.vczz.seckill.redis.keyprefix.OrderKey;
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
    @Autowired
    private RedisService redisService;

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
        boolean isSuccess = iGoodsService.reduceStock(skGoodsVo);
        if (isSuccess){
            //创建订单，普通订单以及秒杀订单两个
            return iOrderService.createOrder(user, skGoodsVo);
        }else {
            //否则就是没库存了，设置秒杀完了
            setGoodsOver(skGoodsVo.getId());
            return null;
        }
    }
    /**
     * 获取秒杀结果
     * @param userId
     * @param goodsId
     * @return
     */
    public long getMiaoShaResult(Long userId, long goodsId){
        SKOrder skOrder = iOrderService.getSKOrderByUIdAndGoodsId(userId, goodsId);
        if (skOrder != null){
            return skOrder.getOrderId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver){
                return -1;
            }else {
                return 0;
            }
        }
    }
    //设置内存标记，商品已经秒杀完
    private void setGoodsOver(long goodsId){
        redisService.set(MiaoShaKey.GOODS_OVER, ""+goodsId, true);
    }
    //判断是否卖完
    private boolean getGoodsOver(long goodsId){
        return redisService.exists(MiaoShaKey.GOODS_OVER, ""+goodsId);
    }
}
