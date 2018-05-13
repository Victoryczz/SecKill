package seu.vczz.seckill.service;

import seu.vczz.seckill.common.CodeMsg;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.vo.LoginVo;

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
    boolean login(LoginVo loginVo);

}
