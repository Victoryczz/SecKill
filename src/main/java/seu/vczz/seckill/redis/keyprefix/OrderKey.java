package seu.vczz.seckill.redis.keyprefix;

/**
 * CREATE by vczz on 2018/5/12
 * orderkey前缀
 */
public class OrderKey extends AbstractKeyPrefix{

    public OrderKey(String prefix){
        super(prefix);
    }

    public static OrderKey GET_ORDER_BY_USER_GOODS_ID = new OrderKey("ugid：");
}
