package seu.vczz.seckill.service;

import seu.vczz.seckill.domain.Order;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.vo.SKGoodsVo;

import java.awt.image.BufferedImage;

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

    /**
     * 获取秒杀结果
     * @param userId
     * @param goodsId
     * @return
     */
    long getMiaoShaResult(Long userId, long goodsId);
    /**
     * 生成验证码图像
     * @param user
     * @param goodsId
     * @return
     */
    BufferedImage createVerifyCode(User user, long goodsId);
    /**
     * 验证验证码
     * @param user
     * @param goodsId
     * @param verifyCode
     * @return
     */
    boolean checkVerifyCode(User user, long goodsId, int verifyCode);
}
