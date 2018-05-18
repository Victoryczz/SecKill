package seu.vczz.seckill.redis.keyprefix;


/**
 * CREATE by vczz on 2018/5/18
 * 访问限制key
 */
public class AccessKey extends AbstractKeyPrefix{



    public AccessKey(int expireSeconds, String prefix){
        super(expireSeconds, prefix);
    }

    public static AccessKey withExpire(int expire){
        return new AccessKey(expire, "access:");
    }

}
