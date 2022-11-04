package com.xxxx.xrpc.rpc.netty;

import cn.hutool.json.JSONUtil;
import com.xxxx.xrpc.rpc.to.Transfer;
import com.xxxx.xrpc.rpc.util.Methods;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @Author: xuepeiquan
 * @Date: 2022/10/13
 * @Time: 20:45
 * @Description
 */

// todo 注意，没有放spring容器，因为服务端是一个线程对应一个通信，对应一个channel的。多个客户端连接时，用的handler不同
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {


    // 收到客户端连接
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info (ctx.channel().remoteAddress() + " 上线了~");

    }


    @Override
    public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception {

        // 注意！msg是ByteBuffer，需要先转一下
        ByteBuf buf = (ByteBuf) msg;
        String str = buf.toString(CharsetUtil.UTF_8);
        log.info ("客户端发送的消息，json："+str);

        // 将json字符串转成 transfer
        Transfer transfer = JSONUtil.toBean (str,Transfer.class);

        log.info ("客户端发送的消息,，对象："+transfer);

        if(transfer != null){

            // 验证通过，执行方法
            if("XRPC".equals (transfer.getPref ())){
                String transferMethodName = transfer.getMethodName ();
                Method m = Methods.methods.get (transferMethodName);

                if(m != null){
                    if(m.getName ().equals (transferMethodName)){
                        log.info ("找到本地和客户端请求方法匹配的方法");

                        // 通过方法名，找到存在Methods.objects里面的对象
                        Object o = Methods.objects.get (transferMethodName);
                        // 利用反射调用函数
                        Object result = m.invoke (o,transfer.getParams ());

                        //将方法调用的结果封装到Transfer，然后转成json，转成二进制发送
                        // todo 发送的方法结果是复杂对象怎么办 【没有问题，json可以转成其他的对象】
                        Transfer transferRes = new Transfer (transferMethodName, result);
                        String json = JSONUtil.parse (transferRes).toString ();
                        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

                        log.info("服务端要返回的消息："+transferRes+" ，json格式："+ json);
                        ctx.writeAndFlush (Unpooled.copiedBuffer (bytes));
                        return;
                    }
                }

            }
        }

        log.info ("客户端调用本地方法失败");
    }

    @Override
    public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception {
        ctx.close ();
    }
}
