package seu.vczz.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.service.IGoodsService;
import seu.vczz.seckill.service.IMiaoShaService;
import seu.vczz.seckill.util.JsonUtil;
import seu.vczz.seckill.vo.SKGoodsVo;
/**
 * CREATE by vczz on 2018/5/16
 * MQreceiver接受者
 */
@Service("mqReceiver")
@Slf4j
public class MQReceiver {

    @Autowired
    private IMiaoShaService iMiaoShaService;
    @Autowired
    private IGoodsService iGoodsService;

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receiveSecKillMessage(String msg){
        log.info("receive miaoShaMessage:{}", msg);
        MiaoShaMessage miaoShaMessage = JsonUtil.stringToObj(msg, MiaoShaMessage.class);
        //收到消息应该下订单了
        User user = miaoShaMessage.getUser();
        Long goodsId = miaoShaMessage.getGoodsId();
        SKGoodsVo skGoodsVo = iGoodsService.getSKGoodsByGoodsId(goodsId);
        //这里可以访问mysql重新查看库存以及是否秒杀过，也可以直接下单
        iMiaoShaService.miaosha(user, skGoodsVo);
    }
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
