package seu.vczz.seckill.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.redis.keyprefix.IKeyPrefix;
import seu.vczz.seckill.redis.keyprefix.UserKey;
import seu.vczz.seckill.util.JsonUtil;

/**
 * CREATE by vczz on 2018/5/11
 * Redis服务类
 */
@Service("redisService")
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    /**
     * redis中获取对象
     * @param keyPrefix 前缀
     * @param key key
     * @param clazz 需要获得的对象的类
     * @param <T> 具体类
     * @return
     */
    public <T> T get(IKeyPrefix keyPrefix, String key, Class<T> clazz){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //真正的key
            String realKey = keyPrefix.getPrefix()+key;
            //获取value
            String value = jedis.get(realKey);
            //转换为java对象返回
            T t = JsonUtil.stringToObj(value, clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * redis设置键值
     * @param keyPrefix 前缀
     * @param key 键
     * @param obj 对应的对象
     * @param <T> 类
     * @return
     */
    public <T> boolean set(IKeyPrefix keyPrefix, String key, T obj){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //obj转字符串
            String value = JsonUtil.objToString(obj);
            if (StringUtils.isEmpty(value)){
                return false;
            }
            //realKey
            String realKey = keyPrefix.getPrefix()+key;
            //取得该前缀下的key的有效时间
            int seconds = keyPrefix.expireSeconds();
            if (seconds <= 0){
                //永久有效
                jedis.set(realKey, value);
            }else {
                //设置带有有效时间
                jedis.setex(realKey, seconds, value);
            }
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断key是否存在
     * @param keyPrefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> boolean exists(IKeyPrefix keyPrefix, String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = keyPrefix.getPrefix()+key;
            return jedis.exists(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 简单的删除
     * @param keyPrefix 前缀
     * @param key 键
     * @return
     */
    public boolean delete(IKeyPrefix keyPrefix, String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = keyPrefix.getPrefix()+key;
            long result = jedis.del(realKey);
            return result > 0;//>0代表成功
        }finally {
            returnToPool(jedis);
        }
    }


    /**
     * redis连接归还给连接池
     * @param jedis
     */
    private void returnToPool(Jedis jedis){
        if (jedis != null){
            //close方法就是归还给线程池，jedis2.9版本以后使用close即可
            jedis.close();
        }

    }
}
