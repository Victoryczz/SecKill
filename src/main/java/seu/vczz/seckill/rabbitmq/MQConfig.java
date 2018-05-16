package seu.vczz.seckill.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CREATE by vczz on 2018/5/16
 * 配置
 */
@Configuration
public class MQConfig {

    public static final String QUEUE = "queue";


    @Bean
    public Queue queue(){
        //是否持久化
        return new Queue(QUEUE, true);
    }


}
