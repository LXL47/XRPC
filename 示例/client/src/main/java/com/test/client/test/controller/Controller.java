package com.test.client.test.controller;

import com.test.client.test.client.XRPCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: xuepeiquan
 * @Date: 2022/11/3
 * @Time: 16:13
 * @Description
 */

// todo 用来测试的接口
@RestController
@RequestMapping("/1")
public class Controller {

    @Autowired
    XRPCService xrpcService;

    // 正确结果是:   服务端收到此消息并回传：ss
    @RequestMapping("/1")
    public String test(){
        String s = xrpcService.serverMethod ("ss");

        System.out.println (s);
        return s;
    }

    // 正确结果是:   [{"1":"嘿嘿嘿","2":"很开心"},{"1":"呜呜呜","2":"好伤心"}]
    @RequestMapping("/2")
    public String test2(){
        List<HashMap<String, Object>> list = xrpcService.serverMethod2 ();
        System.out.println ("list的ToString: "+list);

        return list.toString ();
    }
}
