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

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

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

    /**
     * 生成验证码图像
     * @param user
     * @param goodsId
     * @return
     */
    public BufferedImage createVerifyCode(User user, long goodsId) {
        if(user == null || goodsId <=0) {
            return null;
        }
        int width = 80;
        int height = 32;
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        //把验证码存到redis中
        int rnd = calc(verifyCode);
        redisService.set(MiaoShaKey.MIAOSHA_VERIFY_CODE, user.getId()+"_"+goodsId, rnd);
        //输出图片
        return image;
    }

    /**
     * 验证验证码
     * @param user
     * @param goodsId
     * @param verifyCode
     * @return
     */
    public boolean checkVerifyCode(User user, long goodsId, int verifyCode) {
        if(user == null || goodsId <=0) {
            return false;
        }
        Integer codeOld = redisService.get(MiaoShaKey.MIAOSHA_VERIFY_CODE, user.getId()+"_"+goodsId, Integer.class);
        if(codeOld == null || codeOld - verifyCode != 0 ) {
            return false;
        }
        redisService.delete(MiaoShaKey.MIAOSHA_VERIFY_CODE, user.getId()+"_"+goodsId);
        return true;
    }
    //计算验证码结果
    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(exp);
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    private static char[] ops = new char[] {'+', '-', '*'};
    /**
     * + - *
     * */
    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = ""+ num1 + op1 + num2 + op2 + num3;
        return exp;
    }
}
