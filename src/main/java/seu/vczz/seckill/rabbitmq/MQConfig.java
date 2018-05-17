package seu.vczz.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * CREATE by vczz on 2018/5/16
 * 配置
 */
@Configuration
@Slf4j
public class MQConfig {

    //直连模式
    public static final String QUEUE = "queue";
    //topic Exchange模式
    public static final String TOPIC_1 = "topic_1";
    public static final String TOPIC_2 = "topic_2";
    public static final String TOPIC_EXCHANGE = "topicExchange";
    //fanout模式
    public static final String FANOUT_EXCHANGE = "fanoutExchange";
    //headers模式
    public static final String HEADERS_EXCHANGE = "headersExchange";
    public static final String HEADERS_QUEUE = "headersQueue";

    /**
     * 直连模式
     * @return
     */
    @Bean
    public Queue queue(){
        //是否持久化
        return new Queue(QUEUE, true);
    }

    /**
     * exchange模式
     * @return
     */
    @Bean
    public Queue topicQueue1(){
        return new Queue(TOPIC_1, true);
    }
    @Bean
    public Queue topicQueue2(){
        return new Queue(TOPIC_2, true);
    }
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }
    //绑定规则
    @Bean
    public Binding topicBinding1(){
        //topic_queue_1绑定以topic.key1开始的消息
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.key1");
    }
    @Bean
    public Binding topicBinding2() {
        //所有以topic.开头的消息都发送给topicQueue2
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");
    }
    /**
     * Fanout模式，其实就是广播模式
     */
    //依然使用上面的两个queue
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }
    //绑定规则
    @Bean
    public Binding fanoutBinding1(){
        //topic_queue_1绑定以topic.key1开始的消息
        return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
    }
    @Bean
    public Binding fanoutBinding2() {
        //所有以topic.开头的消息都发送给topicQueue2
        return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
    }
    /**
     * Headers模式
     */
    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange(HEADERS_EXCHANGE);
    }
    @Bean
    public Queue headersQueue(){
        return new Queue(HEADERS_QUEUE, true);
    }
    @Bean
    public Binding headersBinding(){
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("vczz", "aaa");
        headerMap.put("vvvv", "czz");
        //只有消息的头部math这个map的所有部分，才会将消息放到queue中
        return BindingBuilder.bind(headersQueue()).to(headersExchange()).whereAll(headerMap).match();
    }


}
