package seu.vczz.seckill.redis.keyprefix;

/**
 * CREATE by vczz on 2018/5/14
 * 商品前缀
 */
public class GoodsKey extends AbstractKeyPrefix {

    //缓存都是30分钟
    private static final int EXPIRE_TIME = 60*10;

    private GoodsKey(int expireSeconds, String prefix){
        super(expireSeconds, prefix);
    }
    //商品列表
    public static GoodsKey GOODS_LIST = new GoodsKey(EXPIRE_TIME, "goodsList");
    //商品详情
    public static GoodsKey GOODS_DETAIL = new GoodsKey(EXPIRE_TIME, "goodsDetail:");
    //商品库存
    public static GoodsKey GOODS_STOCK = new GoodsKey(EXPIRE_TIME, "goodsStock:");


}
