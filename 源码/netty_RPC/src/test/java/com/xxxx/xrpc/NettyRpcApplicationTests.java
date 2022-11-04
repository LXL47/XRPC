package com.xxxx.xrpc;

import com.xxxx.xrpc.rpc.config.XConfig;
import com.xxxx.xrpc.rpc.netty.Server;
import com.xxxx.xrpc.rpc.util.Methods;
import com.xxxx.xrpc.test.client.XRPCService;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NettyRpcApplicationTests {

    // 测试XConfig是否能获得配置的数据
    @Autowired
    private XConfig xConfig;

    @Test
    void XConfig(){
        System.out.println (xConfig.getRemoteHost ());
        System.out.println (xConfig.getRemotePort ());
        System.out.println (xConfig.getServerPort ());
        //System.out.println (xConfig.ClassPath);
    }


    // 测试 listMethods 是否能获得所有配置了 @XServer注解的方法
    @Autowired
    private Server server;

    @Test
    void listMethods(){
        System.out.println (Methods.methods);
    }






}
