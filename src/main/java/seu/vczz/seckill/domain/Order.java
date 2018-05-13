package seu.vczz.seckill.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * CREATE by vczz on 2018/5/13
 * 订单
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    //id
    private Long id;
    //用户ID
    private Long userId;
    //商品ID
    private Long goodsId;
    //收获地址
    private Long  deliveryAddrId;
    //商品名称
    private String goodsName;
    //商品数量
    private Integer goodsCount;
    //商品价格
    private Double goodsPrice;
    //订单支付方式
    private Integer orderChannel;
    //订单状态
    private Integer status;
    //创建时间
    private Date createDate;
    //支付时间
    private Date payDate;

}
