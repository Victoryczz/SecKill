package seu.vczz.seckill.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import seu.vczz.seckill.common.CodeMsg;
import seu.vczz.seckill.common.ServerResponse;
import java.util.List;

/**
 * CREATE by vczz on 2018/5/13
 * 全局异常处理
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    //代表拦截所有的异常,Exception.class
    @ExceptionHandler(value = Exception.class)
    public ServerResponse<String> exceptionHandler(Exception e){
        //如果是绑定异常，也就是数据校验的时候异常
        if (e instanceof BindException){
            BindException exception = (BindException) e;
            //取到异常信息
            List<ObjectError> errors = exception.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            //niubility
            return ServerResponse.error(CodeMsg.BIND_EXCEPTION.fillArgs(msg));
        }else if (e instanceof GlobalException){
            //如果是GlobalException，也就是业务上的错误
            GlobalException globalException = (GlobalException) e;
            //niubility
            return ServerResponse.error(globalException.getCodeMsg());
        }else if (e instanceof DuplicateKeyException){
            //如果是重复的键，就是重复下单
            DuplicateKeyException exception = (DuplicateKeyException) e;
            String str = exception.getMessage();
            log.error(str);
            return ServerResponse.error(CodeMsg.REPEATE_MIAOSHA);
        }
        else {
            e.printStackTrace();
            //如果不是绑定异常，就返回通用的错误
            return ServerResponse.error(CodeMsg.SERVER_ERROR);
        }
    }

}
