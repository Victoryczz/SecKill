package seu.vczz.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import seu.vczz.seckill.common.ServerResponse;
import seu.vczz.seckill.domain.User;

/**
 * CREATE by vczz on 2018/5/14
 * 用来测试获取用户的QPS
 */
@Controller
@RequestMapping("/user")
public class UserController {


    /**
     * 测试用户信息QPS
     * @param user
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public ServerResponse<User> userInfo(User user){
        return ServerResponse.success(user);
    }

}
