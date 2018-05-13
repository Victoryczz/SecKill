package seu.vczz.seckill.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.service.IUserService;
import seu.vczz.seckill.util.CookieUtil;


/**
 * CREATE by vczz on 2018/5/13
 * 商品页
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IUserService iUserService;

    /**
     * 商品列表，登录过后，
     * token可能通过cookie传递，也可能通过get参数传递
     * @param model
     * @param cookieToken
     * @param paramToken
     * @return
     */
    @RequestMapping("/to_list")
    public String goodsList(Model model,
                            @CookieValue(value = CookieUtil.COOKIE_NAME_TOKEN, required = false)String cookieToken,
                            @RequestParam(value = CookieUtil.COOKIE_NAME_TOKEN, required = false)String paramToken){
        //其实应该从cookie中拿token，然后根据token拿user
        //model.addAttribute("user", new User());
        //先进行参数判断
        if (StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)){
            //如果两个都是空，则返回登录页面
            return "login";
        }
        //如果请求参数中token是空，使用cookie中的token
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        //从redis中取user
        User user = iUserService.getByToken(token);
        model.addAttribute("user", user);
        return "goods_list";
    }

}
