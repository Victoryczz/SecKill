package seu.vczz.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import seu.vczz.seckill.common.CodeMsg;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.service.IGoodsService;
import seu.vczz.seckill.vo.SKGoodsVo;


/**
 * CREATE by vczz on 2018/5/14
 * 秒杀Controller
 */
@Controller
@RequestMapping("/miaosha")
public class SeckillController {

    @Autowired
    private IGoodsService iGoodsService;

    @RequestMapping("/do_miaosha")
    public String miaosha(Model model, User user,@RequestParam("goodsId") Long goodsId){
        model.addAttribute("user", user);
        //如果用户不存在，则跳转到登录页面
        if (user == null){
            return "login";
        }
        //判断库存
        SKGoodsVo skGoodsVo = iGoodsService.getSKGoodsByGoodsId(goodsId);
        int stock = skGoodsVo.getStockCount();
        if (stock <= 0){
            //如果没有库存了
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }

        return null;
    }
}
