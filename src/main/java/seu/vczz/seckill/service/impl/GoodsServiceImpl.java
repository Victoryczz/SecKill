package seu.vczz.seckill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seu.vczz.seckill.dao.GoodsDao;
import seu.vczz.seckill.domain.SKGoods;
import seu.vczz.seckill.service.IGoodsService;
import seu.vczz.seckill.vo.SKGoodsVo;

import java.util.List;

/**
 * CREATE by vczz on 2018/5/13
 * 商品服务
 */
@Service("iGoodsService")
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private GoodsDao goodsDao;

    /**
     * 查询所有的秒杀商品
     * @return
     */
    public List<SKGoodsVo> listSKGoodsVo(){
        return goodsDao.listSKGoodsVo();
    }
    /**
     * 查询商品详情
     * @param goodsId
     * @return
     */
    public SKGoodsVo getSKGoodsByGoodsId(Long goodsId){
        return goodsDao.getSKGoodsByGoodsId(goodsId);
    }
    /**
     * 减少商品库存
     * @param skGoodsVo
     */
    public void reduceStock(SKGoodsVo skGoodsVo){
        SKGoods skGoods = new SKGoods();
        skGoods.setGoodsId(skGoodsVo.getId());
        goodsDao.reduceStock(skGoods);
    }

}
