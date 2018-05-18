package seu.vczz.seckill.access;

import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * CREATE by vczz on 2018/5/18
 * 接口访问限制注解
 */


@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLimit {
    //几秒
    int seconds();
    //访问几次
    int maxCount();
    //是否需要登录
    boolean needLogin() default true;

}
