package seu.vczz.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import seu.vczz.seckill.common.ServerResponse;
import seu.vczz.seckill.rabbitmq.MQSender;

/**
 * CREATE by vczz on 2018/5/10
 * 测试类
 */

@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    private MQSender mqSender;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name", "vczz");
        return "hello";
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
