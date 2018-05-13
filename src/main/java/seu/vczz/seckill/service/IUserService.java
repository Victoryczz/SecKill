package seu.vczz.seckill.service;

import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

/**
 * CREATE by vczz on 2018/5/12
 * 用户类接口
 */
public interface IUserService {


    /**
     * 通过id获取User
     * @param id
     * @return
     */
    User getById(long id);
    /**
     * 登录验证
     * @param loginVo
     * @return
     */
    boolean login(HttpServletResponse response, LoginVo loginVo);
    /**
     * 通过token获取user
     * @param token
     * @return
     */
    User getByToken(String token);

}
