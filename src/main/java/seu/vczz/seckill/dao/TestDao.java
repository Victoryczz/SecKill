package seu.vczz.seckill.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import seu.vczz.seckill.domain.Test;

/**
 * CREATE by vczz on 2018/5/11
 */
@Mapper
public interface TestDao {

    @Select("select * from seckill_test where id = #{id}")
    Test selectTestById(@Param("id") int id);

}
