package seu.vczz.seckill.common;

/**
 * CREATE by vczz on 2018/5/10
 * 错误代码以及对应的提示信息
 */
public class CodeMsg {
    //代码
    private int code;
    //信息
    private String msg;
    //通用的异常,使用中进行扩展
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    //登录模块
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或已经失效");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
    public static CodeMsg MOBILE_PARAM_ERROR = new CodeMsg(500213, "手机号码格式错误");
    public static CodeMsg USER_NOT_EXISTS = new CodeMsg(500214, "用户不存在");
    public static CodeMsg PASSWORD_WRONG = new CodeMsg(500215, "密码错误");


    /**
     * 构造函数
     * @param code 代码
     * @param msg 信息
     */
    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
