package seu.vczz.seckill.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * CREATE by vczz on 2018/5/13
 * CookieUtil,分布式session
 */
public class CookieUtil {

    public static final String COOKIE_NAME_TOKEN = "seckill_login_token";

    private static final String COOKIE_DOMAIN = "seckill.com";

    /**
     * 写cookie
     * @param response
     * @param expireSeconds
     * @param token
     */
    public static void writeLoginCookie(HttpServletResponse response, int expireSeconds, String token){
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        //有效期
        cookie.setMaxAge(expireSeconds);
        //
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

}
