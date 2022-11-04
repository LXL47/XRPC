package com.test.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// todo 注意启动类这里要加上 xrpc的包路径 不然用不了
@SpringBootApplication(scanBasePackages = {"com.test.server","com.xxxx.xrpc.rpc"})
@EnableAspectJAutoProxy(exposeProxy=true)
public class ServerApplication {

    public static void main( String[] args ) {
        SpringApplication.run (ServerApplication.class, args);
    }

}
