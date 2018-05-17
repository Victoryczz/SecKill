package seu.vczz.seckill.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seu.vczz.seckill.common.CodeMsg;
import seu.vczz.seckill.dao.UserDao;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.exception.GlobalException;
import seu.vczz.seckill.redis.RedisService;
import seu.vczz.seckill.redis.keyprefix.UserKey;
import seu.vczz.seckill.service.IUserService;
import seu.vczz.seckill.util.CookieUtil;
import seu.vczz.seckill.util.MD5Util;
import seu.vczz.seckill.util.UUIDUtil;
import seu.vczz.seckill.vo.LoginVo;
import javax.servlet.http.HttpServletResponse;

/**
 * CREATE by vczz on 2018/5/12
 * 用户业务类
 */
@Service("iUserService")
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisService redisService;

    /**
     * 通过id获取User,对象存储的时候，放在redis中，永久有效
     * @param id
     * @return
     */
    public User getById(long id){
        //取缓存
        User user = redisService.get(UserKey.ID, ""+id, User.class);
        if (user != null){
            return user;
        }
        //没有
        user = userDao.getById(id);
        if (user != null){
            redisService.set(UserKey.ID, ""+id, user);
            return user;
        }
        log.warn("-----------------no such user by id--------"+id);
        return null;
    }

    /**
     * 登录验证,改进前返回的是CodeMsg，改进之后，应该直接返回异常或者是boolean
     * @param loginVo
     * @return
     */
    public boolean login(HttpServletResponse response, LoginVo loginVo){
        //不需要判断loginVo是否为空，因为前面controller已经判断了密码和手机号
        String mobile = loginVo.getMobile();
        String inputPass = loginVo.getPassword();
        //判断手机号是否存在，这一步也是优化，没有从数据库中取，而是先从redis中取，没有则去访问数据库
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
        //做分布式session处理
        String token = UUIDUtil.uuid();
        //写进redis,key为前缀+uuid，value为user
        if (!redisService.set(UserKey.TOKEN, token, user)){
            //如果设置失败
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }
        //写cookie
        CookieUtil.writeLoginCookie(response, UserKey.TOKEN.expireSeconds(), token);
        return true;
    }

    /**
     * 通过token获取user
     * @param token
     * @return
     */
    public User getByToken(String token){
        if (StringUtils.isEmpty(token)){
            return null;
        }
        return redisService.get(UserKey.TOKEN, token, User.class);
    }

}
