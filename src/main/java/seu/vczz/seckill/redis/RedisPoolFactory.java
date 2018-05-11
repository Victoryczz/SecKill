package seu.vczz.seckill.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * CREATE by vczz on 2018/5/11
 * 工厂类，用来构造JedisPool
 */
@Configuration
public class RedisPoolFactory {

    @Autowired
    private RedisConfig redisConfig;

    @Bean
    public JedisPool jedisPoolFactory(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大空闲线程数量
        jedisPoolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        //线程池最大数量
        jedisPoolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        //最大等待时间，毫秒计
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait()*1000);
        //各种构造函数
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeout()*1000);
        return jedisPool;
    }


}
