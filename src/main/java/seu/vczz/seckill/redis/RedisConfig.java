package seu.vczz.seckill.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * CREATE by vczz on 2018/5/11
 * Redis配置文件,学习使用spring boot的配置方法
 */
@Component()
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {

    private String host;
    private int port;
    private int timeout;//秒
    private int poolMaxTotal;
    private int poolMaxIdle;
    private int poolMaxWait;//秒

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getPoolMaxTotal() {
        return poolMaxTotal;
    }

    public void setPoolMaxTotal(int poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
    }

    public int getPoolMaxIdle() {
        return poolMaxIdle;
    }

    public void setPoolMaxIdle(int poolMaxIdle) {
        this.poolMaxIdle = poolMaxIdle;
    }

    public int getPoolMaxWait() {
        return poolMaxWait;
    }

    public void setPoolMaxWait(int poolMaxWait) {
        this.poolMaxWait = poolMaxWait;
    }
}
