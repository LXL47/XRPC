package com.xxxx.xrpc.rpc.netty;

import com.xxxx.xrpc.rpc.config.XConfig;
import com.xxxx.xrpc.rpc.to.Transfer;
import com.xxxx.xrpc.rpc.util.ExecutorUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * @Author: xuepeiquan
 * @Date: 2022/10/13
 * @Time: 19:00
 * @Description
 */

// 用于底层发请求给服务端
@Component
@Slf4j
public class Client {

    @Autowired
    private XConfig xConfig;

    // 注意！clientHandler是单例，多线程用的都是同一个clientHandler对象
    @Autowired
    private ClientHandler clientHandler;

    // 异步初始化【是为了等XConfig对象创建后，再调用，直接init()。会导致null报错】
    public Client() {
        new Thread (new Runnable () {
            @Override
            public void run() {
                try {
                    Thread.sleep (3000);
                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }

                if(xConfig.getRemoteHost () != null || xConfig.getRemotePort () != 0) {
                    log.info ("配置远程服务端 地址或端口，打开客户端");
                    init ();
                }
            }
        }).start ();
    }


    // 给其他方法提供代理
    public Transfer proxy( Transfer transfer ) {

        Object result = null;
        Future future;

        // 锁住，同一时间只能一个线程添加 transfer
        synchronized (clientHandler.transferTos) {
            clientHandler.transferTos.add (transfer);

            // 提交任务给线程池
            future = ExecutorUtil.executor.submit (clientHandler);
        }

        try {

            result = future.get ();
            log.info ("proxy方法，服务器结果："+result);
        } catch (Exception e) {
            e.printStackTrace ();
        }

        return (Transfer) result;
    }

    public void init(){

        EventLoopGroup group = new NioEventLoopGroup (1);

        try {
            Bootstrap bootstrap = new Bootstrap ();
            bootstrap.group (group)
                    .channel (NioSocketChannel.class)
                    .handler (new ChannelInitializer<SocketChannel> () {

                        @Override
                        protected void initChannel( SocketChannel socketChannel ) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline ();

                            pipeline.addLast (clientHandler);
                        }
                    });

            log.info ("客户端,服务端地址和端口:"+ xConfig.getRemoteHost () + xConfig.getRemotePort ());
            log.info ("客户端初始化完成");

            ChannelFuture channelFuture = bootstrap.connect (xConfig.getRemoteHost (), xConfig.getRemotePort ()).sync ();
            channelFuture.channel ().closeFuture ().sync ();



        } catch (Exception e){

            e.printStackTrace ();

        } finally {
            group.shutdownGracefully ();
        }

    }

}
