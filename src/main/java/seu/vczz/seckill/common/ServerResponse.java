package seu.vczz.seckill.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * CREATE by vczz on 2018/5/10
 * 服务端响应,自己写的,貌似很难受
 */
public class ServerResponse <T> {

    //响应码,0代表成功
    private int code;
    //响应消息
    private String message;
    //响应数据
    private T data;


    //带data的success响应
    public ServerResponse(T data) {
        this.code = 0;
        this.data = data;
        this.message = "success";
    }
    //普通错误
    public ServerResponse(CodeMsg codeMsg) {
        if (codeMsg == null){
            return;
        }
        this.code = codeMsg.getCode();
        this.message = codeMsg.getMsg();
    }

    /**
     * 成功的响应
     */
    public static <T> ServerResponse<T> success(T data){
        return new ServerResponse<T>(data);
    }
    //只返回成功状态码以及消息
    public static <T> ServerResponse<T> successMsg(CodeMsg codeMsg){
        return new ServerResponse<T>(CodeMsg.SUCCESS);
    }

    /**
     * 失败的响应
     */
    public static <T> ServerResponse<T> error(CodeMsg codeMsg){
        return new ServerResponse<T>(codeMsg);
    }

    /**
     * 判断是否是成功响应
     */
    @JsonIgnore
    public boolean isSuccess(){
        return this.getCode() == 0;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
