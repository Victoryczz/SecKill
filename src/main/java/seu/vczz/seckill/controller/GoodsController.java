package seu.vczz.seckill.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.service.IUserService;



/**
 * CREATE by vczz on 2018/5/13
 * 商品页
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {



    /**
     * 商品列表，登录过后，命名没有传递user，怎么拿到user的呢，看UserArgumentResolver
     * token可能通过cookie传递，也可能通过get参数传递
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("/to_list")
    public String goodsList(Model model, User user){
        model.addAttribute("user", user);
        return "goods_list";
    }

}
