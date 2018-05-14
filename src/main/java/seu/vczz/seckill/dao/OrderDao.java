package seu.vczz.seckill.dao;

import org.apache.ibatis.annotations.*;
import seu.vczz.seckill.domain.Order;
import seu.vczz.seckill.domain.SKOrder;

/**
 * CREATE by vczz on 2018/5/14
 */
@Mapper
public interface OrderDao {

    /**
     * 根据用户id和商品id判断有没有秒杀到
     * @param userId
     * @param goodsId
     * @return
     */
    @Select("select * from seckill_sk_order where user_id=#{userId} and goods_id=#{goodsId}")
    SKOrder getSKOrderByUIdAndGoodsId(@Param("userId") Long userId,@Param("goodsId") Long goodsId);
    /**
     * 插入订单信息，返回自增ID字段,也就是订单号
     * @param order
     * @return
     */

    @Insert("insert into seckill_order(user_id, goods_id, goods_name, goods_count, goods_price," +
            "order_channel, status, create_date) values(#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, " +
            "#{goodsPrice}, #{orderChannel},#{status},#{createDate})")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    long insertOrder(Order order);
    /**
     * 插入秒杀订单
     * @param skOrder
     * @return
     */
    @Insert("insert into seckill_sk_order (user_id, goods_id, order_id) values (#{userId}, #{goodsId}, #{orderId})")
    int insertSKOrder(SKOrder skOrder);



}
