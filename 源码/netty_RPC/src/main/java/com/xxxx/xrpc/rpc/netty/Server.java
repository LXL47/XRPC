package com.xxxx.xrpc.rpc.netty;

import com.xxxx.xrpc.rpc.annota.XServer;
import com.xxxx.xrpc.rpc.config.XConfig;
import com.xxxx.xrpc.rpc.util.*;
import com.xxxx.xrpc.rpc.util.AnnotationUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @Author: xuepeiquan
 * @Date: 2022/10/13
 * @Time: 20:36
 * @Description
 */
@Component
@Slf4j
public class Server {

    @Autowired
    private XConfig xConfig;

    // 异步初始化【是为了等XConfig对象创建后，再调用，直接init()。会导致null报错】
    public Server() {

        new Thread (new Runnable () {
            @Override
            public void run() {
                try {
                    Thread.sleep (3000);
                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }

                if(xConfig.getServerPort () != 0) {
                    log.info ("配置了本地服务端 端口，打开服务端");
                    init ();
                }
            }
        }).start ();
    }

    public void init(){

        EventLoopGroup bossGroup = new NioEventLoopGroup ();
        EventLoopGroup workerGroup = new NioEventLoopGroup ();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap ();
            serverBootstrap.group (bossGroup,workerGroup)
                    .channel (NioServerSocketChannel.class)
                    .childHandler (new ChannelInitializer<SocketChannel> () {
                        @Override
                        protected void initChannel( SocketChannel socketChannel ) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline ();

                            pipeline.addLast (new ServerHandler());
                        }
                    });

            log.info ("服务端绑定的端口:"+xConfig.getServerPort ());
            log.info ("服务端初始化完成");

            ChannelFuture channelFuture = serverBootstrap.bind (xConfig.getServerPort ()).sync ();
            channelFuture.channel ().closeFuture ().sync ();

        } catch (Exception e){
            e.printStackTrace ();
        } finally {
            bossGroup.shutdownGracefully ();
            workerGroup.shutdownGracefully ();
        }

    }


}
