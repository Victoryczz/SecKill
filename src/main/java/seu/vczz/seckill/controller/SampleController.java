package seu.vczz.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import seu.vczz.seckill.common.ServerResponse;
import seu.vczz.seckill.domain.Test;
import seu.vczz.seckill.rabbitmq.MQSender;
import seu.vczz.seckill.redis.RedisService;
import seu.vczz.seckill.redis.keyprefix.UserKey;
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
    @Autowired
    private MQSender mqSender;

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
        boolean result = redisService.set(UserKey.ID, ""+1, test);
        return ServerResponse.success(result);
    }

    @RequestMapping("/redisGet")
    @ResponseBody
    public ServerResponse<Test> testRedisGet(){
        Test test = redisService.get(UserKey.ID, ""+1, Test.class);
        return ServerResponse.success(test);
    }

    @RequestMapping("/mq")
    @ResponseBody
    public ServerResponse<String> testMQ(){
        mqSender.send("fuck you");
        return ServerResponse.success("hello fuck you");
    }

    @RequestMapping("/mq/topic")
    @ResponseBody
    public ServerResponse<String> topic() {
		mqSender.sendTopic("hello,11111111111111");
        return ServerResponse.success("Hello，world");
    }

    @RequestMapping("/mq/fanout")
    @ResponseBody
    public ServerResponse<String> fanout(){
        mqSender.sendFanout("wawawawaawwaaaaaaaaaaaaaa");
        return ServerResponse.success("fuck");
    }

    @RequestMapping("/mq/headers")
    @ResponseBody
    public ServerResponse<String> header() {
		mqSender.sendHeaders("你好");
        return ServerResponse.success("Hello，world");
    }
}
