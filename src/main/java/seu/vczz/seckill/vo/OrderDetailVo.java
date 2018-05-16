package seu.vczz.seckill.vo;

import lombok.Getter;
import lombok.Setter;
import seu.vczz.seckill.domain.Order;

/**
 * CREATE by vczz on 2018/5/15
 * 前后端分离后，返回给前端的订单Vo
 */
@Setter
@Getter
public class OrderDetailVo {

    private SKGoodsVo goods;
    private Order order;

}
