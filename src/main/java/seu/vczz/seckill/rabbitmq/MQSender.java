package seu.vczz.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
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

    public void send(Object object){
        String msg = JsonUtil.objToString(object);
        log.info("----------------------send_message:"+msg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
    }

}
