package seu.vczz.seckill.vo;

import lombok.Getter;
import lombok.Setter;
import seu.vczz.seckill.domain.User;

/**
 * CREATE by vczz on 2018/5/15
 * 动静分离的detailVo
 */
@Setter
@Getter
public class GoodsDetailVo {

    private int miaoshaStatus;
    private int remainSeconds;
    private SKGoodsVo goods;
    private User user;

}
