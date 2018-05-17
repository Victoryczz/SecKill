package seu.vczz.seckill.rabbitmq;

import lombok.Getter;
import lombok.Setter;
import seu.vczz.seckill.domain.User;

/**
 * CREATE by vczz on 2018/5/17
 * rabbitMQ接收的秒杀消息
 */
@Setter
@Getter
public class MiaoShaMessage {
    //用户
    private User user;
    //商品ID
    private long goodsId;
}
