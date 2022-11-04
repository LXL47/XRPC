package com.test.client;

import com.test.client.test.client.XRPCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// todo 注意启动类这里要加上 xrpc的包路径 不然用不了
@SpringBootApplication(scanBasePackages = {"com.test.client","com.xxxx.xrpc.rpc"})
@EnableAspectJAutoProxy(exposeProxy=true)
public class ClientApplication {

    public static void main( String[] args ) {
        SpringApplication.run (ClientApplication.class, args);
    }

}
