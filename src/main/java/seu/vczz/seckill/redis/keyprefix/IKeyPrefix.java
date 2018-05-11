package seu.vczz.seckill.redis.keyprefix;

/**
 * CREATE by vczz on 2018/5/11
 * 键前缀，使用interface-->abstract class-->class的模板设计模式来构造
 */
public interface IKeyPrefix {

    //定义两个基本的方法
    //键的有效时间
    int expireSeconds();
    //获得键前缀
    String getPrefix();
}
