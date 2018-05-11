package seu.vczz.seckill.redis.keyprefix;

/**
 * CREATE by vczz on 2018/5/11
 * 继承自IKeyPrefix的抽象类
 */
public abstract class AbstractKeyPrefix implements IKeyPrefix{

    //有效时间
    private int expireSeconds;
    //前缀
    private String prefix;

    public AbstractKeyPrefix(String prefix){
        //0表示永不失效
        this(0, prefix);
    }

    public AbstractKeyPrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    //获得有效时间
    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    //获得固定前缀
    @Override
    public String getPrefix() {
        //拿到类名(各个子类不同)
        String className = getClass().getSimpleName();
        return className+":"+prefix;
    }
}
