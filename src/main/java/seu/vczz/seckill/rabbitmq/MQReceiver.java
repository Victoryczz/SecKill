package seu.vczz.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * CREATE by vczz on 2018/5/16
 * MQreceiver接受者
 */
@Service("mqReceiver")
@Slf4j
public class MQReceiver {


    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message){
        log.info("---------------------mq_receive_message:"+message);
    }


}
