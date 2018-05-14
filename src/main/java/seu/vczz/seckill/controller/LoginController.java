package seu.vczz.seckill.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import seu.vczz.seckill.common.CodeMsg;
import seu.vczz.seckill.common.ServerResponse;
import seu.vczz.seckill.service.IUserService;
import seu.vczz.seckill.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * CREATE by vczz on 2018/5/12
 * 登录控制
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private IUserService iUserService;

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    /**
     * 登录
     * @param response
     * @param loginVo
     * @return
     */
    @RequestMapping("/do_login")
    @ResponseBody
    public ServerResponse<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo){
        log.info(loginVo.toString());
        //进行参数校验，其实可以前端来做，只判断是否为空，反正也没有实时校验的功能
        //使用了validator
        //进行业务，登录
        iUserService.login(response, loginVo);
        //由于其他的方法都是抛出异常，所以这里可以直接返回成功
        return ServerResponse.successMsg(CodeMsg.SUCCESS);
    }




}
