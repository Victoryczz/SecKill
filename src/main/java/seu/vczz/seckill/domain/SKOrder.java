package seu.vczz.seckill.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CREATE by vczz on 2018/5/13
 * 秒杀订单
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SKOrder {
    //秒杀订单ID
    private Long id;
    //用户ID
    private Long userId;
    //对应的订单ID
    private Long  orderId;
    //商品ID
    private Long goodsId;

}
