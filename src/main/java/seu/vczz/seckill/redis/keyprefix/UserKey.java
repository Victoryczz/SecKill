package seu.vczz.seckill.redis.keyprefix;

/**
 * CREATE by vczz on 2018/5/11
 * 单个prefix类，全部继承自AbstractKeyPrefix，用户前缀
 * 使用场景：使用redis作为缓存，缓存中通过id或者name可以超找到对应的user
 */
public class UserKey extends AbstractKeyPrefix{
    //token有效期
    private static final int EXPIRE_TIME = 60*30;

    public UserKey(int expireSeconds, String prefix){
        super(expireSeconds, prefix);
    }

    //默认永久有效
    private UserKey(String prefix){
        super(prefix);
    }
    //通过id获得user
    public static UserKey getById = new UserKey("id");
    //通过name获得user
    public static UserKey getByName = new UserKey("name");
    //token
    public static UserKey token = new UserKey(EXPIRE_TIME, "token");

}
