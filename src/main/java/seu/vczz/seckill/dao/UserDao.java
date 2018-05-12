package seu.vczz.seckill.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import seu.vczz.seckill.domain.User;

/**
 * CREATE by vczz on 2018/5/12
 * mapper接口
 */
@Mapper
public interface UserDao {

    @Select("select * from seckill_user where id = #{id}")
    User getById(@Param("id")long id);

}
