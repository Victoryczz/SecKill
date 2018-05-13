package seu.vczz.seckill.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import seu.vczz.seckill.vo.GoodsVo;

import java.util.List;

/**
 * CREATE by vczz on 2018/5/13
 */
@Mapper
public interface GoodsDao {

    /**
     * 查询所有秒杀的商品
     * @return
     */
    @Select("select g.*, skg.stock_count, skg.start_date, skg.end_date form seckill_sk_goods skg left join seckill_goods g on skg.goods_id = g.id")
    List<GoodsVo> listGoodsVo();

}
