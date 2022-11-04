package com.xxxx.xrpc.rpc.netty;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.xxxx.xrpc.rpc.to.Transfer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import jdk.nashorn.internal.parser.JSONParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: xuepeiquan
 * @Date: 2022/10/13
 * @Time: 19:15
 * @Description
 */
@Slf4j
@Component
public class ClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    //@Autowired
    //private Client client;

    // 上下文
    private ChannelHandlerContext ctx;

    // 客户端要发送的消息
    public List<Transfer> transferTos = new ArrayList<> ();

    // 服务端返回的结果
    private HashMap<String,Transfer> results = new HashMap<> ();




    // 连接之后 把ctx赋值给成员变量，让call方法使用
    @Override
    public void channelActive( ChannelHandlerContext ctx ) throws Exception {
        this.ctx = ctx;

        log.info ("客户端连接到服务器");
    }


    @Override
    public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception {

        ByteBuf buf = (ByteBuf) msg;
        String str = buf.toString(CharsetUtil.UTF_8);
        // 将json字符串转回 transfer，存入结果集
        Transfer result = JSONUtil.toBean (str,Transfer.class);

        log.info ("服务端返回的结果，json："+str);
        log.info ("服务端返回的结果，对象："+result);
        results.put (result.getMethodName (),result);
    }

    @Override
    public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception {
        ctx.close ();
    }


    // 作为任务被线程池调用，往服务端发送消息
    @Override
    public Object call() throws Exception {

        log.info (Thread.currentThread ().getName ()+"调用call方法");


        // 每次都从第一个拿，拿完就删
        Transfer transfer = transferTos.get (0);
        transferTos.remove (0);

        // 设置前缀，用于服务端识别
        transfer.setPref ("XRPC");

        // 转成json对象发送
        String transferJSON = JSONUtil.parse (transfer).toString ();
        // 转成btye数组   utf-8模式
        byte[] bytes = transferJSON.getBytes(StandardCharsets.UTF_8);

        log.info ("客户端发送数据，二进制："+ Arrays.toString (bytes));

        // !发送消息一定要用buffer！
        ctx.writeAndFlush (Unpooled.copiedBuffer(transferJSON,  CharsetUtil.UTF_8));


        // 一直轮询，查看有没有需要的结果
        Transfer result;
        while (true){
            if((result = results.get (transfer.getMethodName ())) != null){
                log.info ("call方法，接收到的result："+result);
                return result;
            }
            Thread.sleep (500);
        }

    }


}
