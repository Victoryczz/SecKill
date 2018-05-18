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
    //通用的异常,使用中进行扩展5001XX
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_EXCEPTION = new CodeMsg(500101, "参数校验异常: %s");
    public static CodeMsg ILLEGAL_REQUEST = new CodeMsg(500102, "请求非法");
    //登录模块5002XX
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或已经失效");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
    public static CodeMsg MOBILE_PARAM_ERROR = new CodeMsg(500213, "手机号码格式错误");
    public static CodeMsg USER_NOT_EXISTS = new CodeMsg(500214, "用户不存在");
    public static CodeMsg PASSWORD_WRONG = new CodeMsg(500215, "密码错误");
    public static CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(500216, "访问次数已达到");
    //秒杀模块5003XX
    public static CodeMsg MIAO_SHA_OVER = new CodeMsg(500300, "秒杀已结束");
    public static CodeMsg REPEATE_MIAOSHA = new CodeMsg(500301, "不能重复秒杀");
    public static CodeMsg STOCK_NOT_ENOUGH = new CodeMsg(500302, "库存不足");
    public static CodeMsg ON_LINE = new CodeMsg(500303, "排队中");
    public static CodeMsg PATH_NOT_EXISTS = new CodeMsg(500304, "接口地址不存在");
    public static CodeMsg MIAOSHA_FAILED = new CodeMsg(500305, "秒杀失败");
    public static CodeMsg VERIFY_CODE_WRONG = new CodeMsg(500306, "验证码错误");
    //订单模块5004XX
    public static CodeMsg ORDER_NOT_EXISTS = new CodeMsg(500400, "订单不存在");
    //商品模块5005XX
    /**
     * 构造函数
     * @param code 代码
     * @param msg 信息
     */
    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    /**
     * 填充参数的codeMsg,这方法很奇妙
     * @param args 异常参数
     * @return
     */
    public CodeMsg fillArgs(Object... args){
        int code = this.code;
        String msg = String.format(this.msg, args);
        return new CodeMsg(code, msg);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
