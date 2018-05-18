package seu.vczz.seckill.access;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import seu.vczz.seckill.common.CodeMsg;
import seu.vczz.seckill.common.ServerResponse;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.redis.RedisService;
import seu.vczz.seckill.redis.keyprefix.AccessKey;
import seu.vczz.seckill.service.IUserService;
import seu.vczz.seckill.util.CookieUtil;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * CREATE by vczz on 2018/5/18
 * 拦截器，接口限流以及用户权限管理
 */
@Service("accessInterceptor")
public class AccessInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private RedisService redisService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            User user = getUser(request);
            UserContext.setUser(user);
            HandlerMethod hm = (HandlerMethod)handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if(accessLimit == null) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();
            if(needLogin) {
                if(user == null) {
                    render(response, CodeMsg.SESSION_ERROR);
                    return false;
                }
                key += "_" + user.getId();
            }else {
                //do nothing
            }
            AccessKey ak = AccessKey.withExpire(seconds);
            Integer count = redisService.get(ak, key, Integer.class);
            if(count  == null) {
                redisService.set(ak, key, 1);
            }else if(count < maxCount) {
                redisService.incr(ak, key);
            }else {
                render(response, CodeMsg.ACCESS_LIMIT_REACHED);
                return false;
            }
        }
        return true;
    }

    private void render(HttpServletResponse response, CodeMsg cm)throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str  = JSON.toJSONString(ServerResponse.error(cm));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    private User getUser(HttpServletRequest request) {
        String paramToken = request.getParameter(CookieUtil.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, CookieUtil.COOKIE_NAME_TOKEN);
        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return iUserService.getByToken(token);
    }

    private String getCookieValue(HttpServletRequest request, String cookiName) {
        Cookie[]  cookies = request.getCookies();
        if(cookies == null || cookies.length <= 0){
            return null;
        }
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookiName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
