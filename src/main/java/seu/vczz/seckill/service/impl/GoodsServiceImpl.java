package seu.vczz.seckill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import seu.vczz.seckill.dao.GoodsDao;
import seu.vczz.seckill.service.IGoodsService;

/**
 * CREATE by vczz on 2018/5/13
 * 商品服务
 */
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private GoodsDao goodsDao;

}
