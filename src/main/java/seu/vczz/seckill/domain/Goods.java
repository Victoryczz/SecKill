package seu.vczz.seckill.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * CREATE by vczz on 2018/5/13
 * 商品
 */
@Setter
@Getter
public class Goods {
    //id
    private Long id;
    //商品名
    private String goodsName;
    //商品标题
    private String goodsTitle;
    //商品图片
    private String goodsImg;
    //商品详情
    private String goodsDetail;
    //商品价格
    private Double goodsPrice;
    //商品库存
    private Integer goodsStock;

}
