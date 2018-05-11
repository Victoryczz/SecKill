package seu.vczz.seckill.redis.keyprefix;

/**
 * CREATE by vczz on 2018/5/11
 * 单个prefix类，全部继承自AbstractKeyPrefix，用户前缀
 * 使用场景：使用redis作为缓存，缓存中通过id或者name可以超找到对应的user
 */
public class UserKeyPrefix extends AbstractKeyPrefix{

    //默认永久有效
    private UserKeyPrefix(String prefix){
        super(prefix);
    }
    //通过id获得user
    public static UserKeyPrefix getById = new UserKeyPrefix("id");
    //通过name获得user
    public static UserKeyPrefix getByName = new UserKeyPrefix("name");


}
