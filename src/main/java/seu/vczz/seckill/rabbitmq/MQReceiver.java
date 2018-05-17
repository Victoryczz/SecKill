package seu.vczz.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import seu.vczz.seckill.vo.LoginVo;

import java.util.Map;

/**
 * CREATE by vczz on 2018/5/16
 * MQreceiver接受者
 */
@Service("mqReceiver")
@Slf4j
public class MQReceiver {

    /**
     * 直连
     * @param message
     */
    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message){
        log.info("---------------------mq_receive_message:"+message);
    }
    /**
     * topic
     */
    @RabbitListener(queues=MQConfig.TOPIC_1)
    public void receiveTopic1(String message) {
        log.info(" topic  queue1 message:"+message);
    }
    @RabbitListener(queues=MQConfig.TOPIC_2)
    public void receiveTopic2(String message) {
        log.info(" topic  queue2 message:"+message);
    }
    /**
     * headers模式
     */
    @RabbitListener(queues = MQConfig.HEADERS_QUEUE)
    public void receiveHeaders(byte[] bytes){
        log.info("----------receive headers message: "+ new String(bytes));
    }

}
