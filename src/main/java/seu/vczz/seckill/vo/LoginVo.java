package seu.vczz.seckill.vo;

/**
 * CREATE by vczz on 2018/5/12
 * 测试登录的viewObject
 */
public class LoginVo {

    //得和前端传递的属性名相同才行，用来接收前端传递过来的数据
    private String mobile;

    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
