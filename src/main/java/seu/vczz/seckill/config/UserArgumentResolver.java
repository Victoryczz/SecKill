package seu.vczz.seckill.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.service.IUserService;
import seu.vczz.seckill.util.CookieUtil;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


/**
 * CREATE by vczz on 2018/5/13
 * user参数解析,直接从request中获得user对象
 */
@Service("userArgumentResolver")
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private IUserService iUserService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //先获取参数类型
        Class<?> clazz = methodParameter.getParameterType();
        //判断是否是user类型的，是的话才会进行参数解析
        return clazz == User.class;
    }
    //就是将之前的逻辑拿过来
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        //先将request和response取出来
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        //之前的操作了
        String paramToken = request.getParameter(CookieUtil.COOKIE_NAME_TOKEN);
        String cookieToken = null;
        //拿到cookieToken
        Cookie[] cookies = request.getCookies();
        //cookie可能为空，当然在项目中是这样的，如果cookie为空，就是没有登录
        if (cookies == null || cookies.length == 0){
            return null;
        }
        for (Cookie cookie : cookies){
            if (cookie.getName().equals(CookieUtil.COOKIE_NAME_TOKEN)){
                cookieToken = cookie.getValue();
                break;
            }
        }
        if (StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)){
            //如果两个都是空，则返回null
            return null;
        }
        //优先选择paramToken
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        //从redis中取user
        User user = iUserService.getByToken(token);
        return user;
    }
}
