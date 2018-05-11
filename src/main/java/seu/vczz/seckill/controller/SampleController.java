package seu.vczz.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import seu.vczz.seckill.common.ServerResponse;
import seu.vczz.seckill.domain.Test;
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


}
