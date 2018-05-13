package seu.vczz.seckill.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import seu.vczz.seckill.common.CodeMsg;
import seu.vczz.seckill.common.ServerResponse;
import seu.vczz.seckill.service.IUserService;
import seu.vczz.seckill.util.ValidatorUtil;
import seu.vczz.seckill.vo.LoginVo;

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

    @RequestMapping("/do_login")
    @ResponseBody
    public ServerResponse<String> doLogin(@Valid LoginVo loginVo){
        log.info(loginVo.toString());
        //进行参数校验，其实可以前端来做，只判断是否为空，反正也没有实时校验的功能
        //使用了validator

        /*String inputPass = loginVo.getPassword();
        //密码为空
        if (StringUtils.isEmpty(inputPass)){
            return ServerResponse.error(CodeMsg.PASSWORD_EMPTY);
        }
        //手机号为空
        String mobile = loginVo.getMobile();
        if (StringUtils.isEmpty(mobile)){
            return ServerResponse.error(CodeMsg.MOBILE_EMPTY);
        }
        //判断手机号格式，如是否11位
        if (!ValidatorUtil.isMobile(mobile)){
            return ServerResponse.error(CodeMsg.MOBILE_PARAM_ERROR);
        }*/

        //进行业务，登录
        iUserService.login(loginVo);
        //由于其他的方法都是抛出异常，所以这里可以直接返回成功
        return ServerResponse.successMsg(CodeMsg.SUCCESS);
    }




}
