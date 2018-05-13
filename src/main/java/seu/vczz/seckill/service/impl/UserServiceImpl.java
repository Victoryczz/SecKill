package seu.vczz.seckill.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seu.vczz.seckill.common.CodeMsg;
import seu.vczz.seckill.dao.UserDao;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.exception.GlobalException;
import seu.vczz.seckill.service.IUserService;
import seu.vczz.seckill.util.MD5Util;
import seu.vczz.seckill.vo.LoginVo;

/**
 * CREATE by vczz on 2018/5/12
 * 用户业务类
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    /**
     * 通过id获取User
     * @param id
     * @return
     */
    public User getById(long id){
        return userDao.getById(id);
    }

    /**
     * 登录验证,改进前返回的是CodeMsg，改进之后，应该直接返回异常或者是boolean
     * @param loginVo
     * @return
     */
    public boolean login(LoginVo loginVo){
        //不需要判断loginVo是否为空，因为前面controller已经判断了密码和手机号
        String mobile = loginVo.getMobile();
        String inputPass = loginVo.getPassword();
        //判断手机号是否存在
        User user = getById(Long.parseLong(mobile));
        if (user == null){
            throw new GlobalException(CodeMsg.USER_NOT_EXISTS);
        }
        //验证密码
        //数据库存储的密码
        String dbPass = user.getPassword();
        //数据库存储的salt
        String dbSalt = user.getSalt();
        //计算的password
        String pass = MD5Util.formPassToDBPass(inputPass, dbSalt);
        if (!StringUtils.equals(dbPass, pass)){
            throw new GlobalException(CodeMsg.PASSWORD_WRONG);
        }
        //否则登录成功
        return true;
    }

}
