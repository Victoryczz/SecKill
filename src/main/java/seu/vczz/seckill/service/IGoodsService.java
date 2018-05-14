package seu.vczz.seckill.service;

import seu.vczz.seckill.vo.SKGoodsVo;

import java.util.List;

/**
 * CREATE by vczz on 2018/5/13
 */
public interface IGoodsService {

    /**
     * 查询所有的秒杀商品
     * @return
     */
    List<SKGoodsVo> listSKGoodsVo();
    /**
     * 查询商品详情
     * @param goodsId
     * @return
     */
    SKGoodsVo getSKGoodsByGoodsId(Long goodsId);


}
