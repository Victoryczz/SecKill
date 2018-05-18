package seu.vczz.seckill.redis.keyprefix;


/**
 * CREATE by vczz on 2018/5/17
 */
public class MiaoShaKey extends AbstractKeyPrefix{

    private static final int EXPIRE_TIME = 60*10;

    private MiaoShaKey(int expireSeconds, String prefix){
        super(expireSeconds, prefix);
    }
    //秒杀结束
    public static MiaoShaKey GOODS_OVER = new MiaoShaKey(EXPIRE_TIME, "goods_over:");
    //秒杀地址
    public static MiaoShaKey MIAOSHA_PATH = new MiaoShaKey(EXPIRE_TIME, "mPath:");
    //验证码
    public static MiaoShaKey MIAOSHA_VERIFY_CODE = new MiaoShaKey(EXPIRE_TIME, "vCode:");

}
