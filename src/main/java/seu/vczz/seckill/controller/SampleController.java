package seu.vczz.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import seu.vczz.seckill.common.ServerResponse;
import seu.vczz.seckill.domain.Test;
import seu.vczz.seckill.redis.RedisService;
import seu.vczz.seckill.redis.keyprefix.UserKeyPrefix;
import seu.vczz.seckill.service.ITestService;

/**
 * CREATE by vczz on 2018/5/10
 * 测试类
 */

@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    private ITestService iTestService;
    @Autowired
    private RedisService redisService;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name", "vczz");
        return "hello";
    }

    @RequestMapping("/test")
    @ResponseBody
    public ServerResponse<Test> getTest(){
        Test test = iTestService.getById(1);
        return ServerResponse.success(test);
    }

    @RequestMapping("/redisSet")
    @ResponseBody
    public ServerResponse<Boolean> testRedisSet(){
        Test test = new Test();
        test.setId(1);
        test.setName("vczz");
        boolean result = redisService.set(UserKeyPrefix.getById, ""+1, test);
        return ServerResponse.success(result);
    }

    @RequestMapping("/redisGet")
    @ResponseBody
    public ServerResponse<Test> testRedisGet(){
        Test test = redisService.get(UserKeyPrefix.getById, ""+1, Test.class);
        return ServerResponse.success(test);
    }


}
