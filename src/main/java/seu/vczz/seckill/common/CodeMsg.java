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
