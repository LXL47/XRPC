package com.xxxx.xrpc;

import com.xxxx.xrpc.test.client.XRPCService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy=true)
public class NettyRpcApplication {

    public static void main( String[] args ) {
        SpringApplication.run (NettyRpcApplication.class, args);
    }

}
