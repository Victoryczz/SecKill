package seu.vczz.seckill.controller;


import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.service.IGoodsService;
import seu.vczz.seckill.vo.SKGoodsVo;

import java.util.List;


/**
 * CREATE by vczz on 2018/5/13
 * 商品页
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService iGoodsService;

    /**
     * 商品列表，登录过后，命名没有传递user，怎么拿到user的呢，看UserArgumentResolver
     * token可能通过cookie传递，也可能通过get参数传递
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("/to_list")
    public String skGoodsList(Model model, User user){
        model.addAttribute("user", user);
        //查询秒杀商品列表
        List<SKGoodsVo> skGoodsList = iGoodsService.listSKGoodsVo();
        model.addAttribute("skGoodsList", skGoodsList);
        return "goods_list";
    }

    /**
     * 秒杀商品详情
     * @param goodsId
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("/to_detail/{goodsId}")
    public String detail(@PathVariable("goodsId")Long goodsId, Model model, User user){
        model.addAttribute("user", user);
        //查询goods
        SKGoodsVo skGoodsVo = iGoodsService.getSKGoodsByGoodsId(goodsId);
        model.addAttribute("goods", skGoodsVo);

        long startTime = skGoodsVo.getStartDate().getTime();
        long endTime = skGoodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if (now < startTime){
            //还没开始
            miaoshaStatus = 0;
            remainSeconds = (int) ((startTime-now)/1000);
        }else if (now > endTime){
            //结束了
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {
            //进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        return "goods_detail";
    }

}
