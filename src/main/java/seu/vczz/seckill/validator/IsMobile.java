package seu.vczz.seckill.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * CREATE by vczz on 2018/5/13
 * 自定义@IsMobile注解进行参数校验
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsMobileValidator.class})
public @interface IsMobile {

    //有些情况下不需要手机号
    boolean required() default false;

    String message() default "手机号码格式有误";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
