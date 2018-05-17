package seu.vczz.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seu.vczz.seckill.util.JsonUtil;

/**
 * CREATE by vczz on 2018/5/16
 * MQ发送者，也就是生产者
 */
@Service("mqSender")
@Slf4j
public class MQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送秒杀消息
     * @param miaoShaMessage
     */
    public void sendSecKillMessage(MiaoShaMessage miaoShaMessage){
        String msg = JsonUtil.objToString(miaoShaMessage);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, msg);
    }
    /**
     * 直连模式
     * @param object
     */
    public void send(Object object){
        String msg = JsonUtil.objToString(object);
        log.info("----------------------send_message:"+msg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
    }
    /**
     * topic模式
     */
    public void sendTopic(Object message) {
		String msg = JsonUtil.objToString(message);
		log.info("send topic message:"+msg);
		amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", msg+"1");
		amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", msg+"2");
	}
    /**
     * fanout模式
     */
    public void sendFanout(Object object){
        String msg = JsonUtil.objToString(object);
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", msg);
    }
    /**
     * headers模式
     */
    public void sendHeaders(Object object){
        String msg = JsonUtil.objToString(object);
        MessageProperties properties = new MessageProperties();
		properties.setHeader("vczz", "aaa");
		properties.setHeader("vvvv", "czz");
		Message obj = new Message(msg.getBytes(), properties);
		amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", obj);
    }

}
