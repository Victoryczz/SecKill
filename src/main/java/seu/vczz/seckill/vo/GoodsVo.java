package seu.vczz.seckill.vo;

import lombok.Getter;
import lombok.Setter;
import seu.vczz.seckill.domain.Goods;

import java.util.Date;

/**
 * CREATE by vczz on 2018/5/13
 * 商品VO，集合了普通商品以及秒杀商品,先继承Goods，然后集成SKGoods，强
 */
@Setter
@Getter
public class GoodsVo extends Goods{
    //数量
    private Integer stockCount;
    //秒杀开始时间
    private Date startDate;
    //结束时间
    private Date endDate;
}
