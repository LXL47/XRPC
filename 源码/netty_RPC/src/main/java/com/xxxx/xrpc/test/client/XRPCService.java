package com.xxxx.xrpc.test.client;

import com.xxxx.xrpc.rpc.annota.XClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: xuepeiquan
 * @Date: 2022/10/13
 * @Time: 16:30
 * @Description
 */

// 调用远程服务端的方法
@Component
public class XRPCService {


    @XClient
    public String serverMethod(String s){
        return null;
    }

    @XClient
    public List<HashMap<String,Object>> serverMethod2() {
        return null;
    }
}
