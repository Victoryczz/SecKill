package seu.vczz.seckill.exception;

import seu.vczz.seckill.common.CodeMsg;

/**
 * CREATE by vczz on 2018/5/13
 * 自定义全局异常
 */
public class GlobalException extends RuntimeException{

    //将CodeMsg传进来，这样所有的异常或错误都可以包装成GlobalException
    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg){
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
