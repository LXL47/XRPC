package com.test.server.test.service;


import com.xxxx.xrpc.rpc.annota.XServer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: xuepeiquan
 * @Date: 2022/10/13
 * @Time: 16:33
 * @Description
 */

// todo 提供给客户端的方法
@Component
public class ServerTest {

    @XServer
    public String serverMethod(String s){
        return "服务端收到此消息并回传：" + s;
    }

    @XServer
    public List<HashMap<String,Object>> serverMethod2(){
        HashMap<String, Object> map = new HashMap<> ();
        map.put ("1","嘿嘿嘿");
        map.put ("2","很开心");

        HashMap<String, Object> map2 = new HashMap<> ();
        map2.put ("1","呜呜呜");
        map2.put ("2","好伤心");

        List<HashMap<String,Object>> list = new ArrayList<> ();
        list.add (map);
        list.add (map2);

        return list;
    }

}
