package seu.vczz.seckill.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * CREATE by vczz on 2018/5/13
 * 秒杀商品
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SKGoods {
    //秒杀ID
    private Long id;
    //秒杀商品ID
    private Long goodsId;
    //数量
    private Integer stockCount;
    //秒杀开始时间
    private Date startDate;
    //结束时间
    private Date endDate;

}
