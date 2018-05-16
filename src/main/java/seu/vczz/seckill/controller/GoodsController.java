package seu.vczz.seckill.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import seu.vczz.seckill.common.ServerResponse;
import seu.vczz.seckill.domain.User;
import seu.vczz.seckill.redis.RedisService;
import seu.vczz.seckill.redis.keyprefix.GoodsKey;
import seu.vczz.seckill.service.IGoodsService;
import seu.vczz.seckill.vo.GoodsDetailVo;
import seu.vczz.seckill.vo.SKGoodsVo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private RedisService redisService;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    private ApplicationContext applicationContext;
    /**
     * 商品列表，登录过后，命名没有传递user，怎么拿到user的呢，看UserArgumentResolver
     * token可能通过cookie传递，也可能通过get参数传递
     * 页面缓存
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String skGoodsList(Model model, User user, HttpServletRequest request, HttpServletResponse response){
        model.addAttribute("user", user);
        //先在redis中找缓存，有的话直接返回
        String html = redisService.get(GoodsKey.GOODS_LIST, "", String.class);
        if (!StringUtils.isEmpty(html)){
            return html;
        }
        //没有就手动渲染模板，按照spring boot中thymeleaf的渲染方式手动渲染，放进缓存，然后返回
        //查询秒杀商品列表
        List<SKGoodsVo> skGoodsList = iGoodsService.listSKGoodsVo();
        model.addAttribute("skGoodsList", skGoodsList);

        //手动处理
        SpringWebContext springWebContext = new SpringWebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap(), applicationContext);
        //需要一个IContext
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", springWebContext);
        if (!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.GOODS_LIST, "", html);
        }
        return html;
    }
    /**
     * 秒杀商品详情
     * 页面缓存
     * @param goodsId
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String detail(@PathVariable("goodsId")Long goodsId, Model model, User user,
                         HttpServletRequest request, HttpServletResponse response){
        model.addAttribute("user", user);
        //取缓存
        String html = redisService.get(GoodsKey.GOODS_DETAIL, ""+goodsId, String.class);
        if (!StringUtils.isEmpty(html)){
            return html;
        }
        //手动渲染
        //查询goods
        SKGoodsVo skGoodsVo = iGoodsService.getSKGoodsByGoodsId(goodsId);
        model.addAttribute("goods", skGoodsVo);
        //判断秒杀时间
        long startTime = skGoodsVo.getStartDate().getTime();
        long endTime = skGoodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();
        //秒杀状态和剩余时间
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
        //渲染
        SpringWebContext springWebContext = new SpringWebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap(), applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", springWebContext);
        if (!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.GOODS_DETAIL, ""+goodsId, html);
        }
        return html;
    }
    /**
     * 前后端分离的goodsDetail，也就是使用了页面静态化
     * @param goodsId
     * @param user
     * @return
     */
    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public ServerResponse<GoodsDetailVo> detailStatic(@PathVariable("goodsId")Long goodsId, User user){
        //查询goods
        SKGoodsVo skGoodsVo = iGoodsService.getSKGoodsByGoodsId(goodsId);
        //判断秒杀时间
        long startTime = skGoodsVo.getStartDate().getTime();
        long endTime = skGoodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();
        //秒杀状态和剩余时间
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
        //返回
        GoodsDetailVo goods = new GoodsDetailVo();
        goods.setGoods(skGoodsVo);
        goods.setMiaoshaStatus(miaoshaStatus);
        goods.setRemainSeconds(remainSeconds);
        goods.setUser(user);
        return ServerResponse.success(goods);
    }
}
