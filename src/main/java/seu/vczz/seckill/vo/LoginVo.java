package seu.vczz.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import seu.vczz.seckill.validator.IsMobile;
import javax.validation.constraints.NotNull;

/**
 * CREATE by vczz on 2018/5/12
 * 测试登录的viewObject
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginVo {

    //得和前端传递的属性名相同才行，用来接收前端传递过来的数据
    @NotNull
    @IsMobile//照着@NotNull来自定义一个注解
    private String mobile;

    @NotNull//非空
    @Length(min = 32)//最小长度是2
    private String password;

    @Override
    public String toString() {
        return "LoginVo{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
