package seu.vczz.seckill.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * CREATE by vczz on 2018/5/12
 * 用户实体类
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    //id,手机号
    private Long id;
    //昵称
    private String nickname;
    //密码
    private String password;
    //
    private String salt;
    //头像ID
    private String head;
    //注册日期
    private Date registerDate;
    //登录日期
    private Date lastLoginDate;
    //登录次数
    private Integer loginCount;


}
