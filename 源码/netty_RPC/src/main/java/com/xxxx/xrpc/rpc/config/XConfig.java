package com.xxxx.xrpc.rpc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Author: xuepeiquan
 * @Date: 2022/10/13
 * @Time: 17:24
 * @Description
 */
//@Component
//public class XConfig {
//
//    @Value ("${xrpc.host}")
//    public static String host;
//
//    @Value ("${xrpc.port}")
//    public static int port;
//
//    @Value ("${server.port}")
//    // 本地做为服务端时的端口
//    public static int serverPort;
//
//
//    @Value ("${xrpc.xserver.classPath}")
//    // 扫描指定包下 添加了 @XServer注解的方法
//    public static String XServerClassPath;
//}


//@Component
//public class XConfig {
//
//
//    @Autowired
//    private Environment env;
//
//
//    public String host = env.getProperty("xrpc.host");
//
//    public int port = Integer.parseInt (env.getProperty("xrpc.port"));
//
//    // 本地做为服务端时的端口
//    public int serverPort = Integer.parseInt (env.getProperty("server.port"));
//
//
//    // 扫描指定包下 添加了 @XServer注解的方法
//    public String XServerClassPath = env.getProperty("xrpc.xserver.classPath");
//}

@ConfigurationProperties(prefix="xrpc")
@Component
public class XConfig {

    // 设置XRPC  远程服务端地址 端口
    // 不设置 开不了本地的Client服务,调用不了远程服务端的函数
    private String remoteHost;
    private int remotePort;

    // 设置XRPC  本地远程调用服务端的方法 所在的包
    // 比如我要调用 远程服务端的 method1方法,我就需要在本地的一个包下,新建一个类
    // 然后在类里面加上 要调用远程服务端的方法 加上@XClient注解
    private String classPath;

    // 如果要给其他机器提供服务,设置本地开启服务的端口
    private int serverPort;


    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost( String remoteHost ) {
        this.remoteHost = remoteHost;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public void setRemotePort( int remotePort ) {
        this.remotePort = remotePort;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath( String classPath ) {
        this.classPath = classPath;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort( int serverPort ) {
        this.serverPort = serverPort;
    }
}