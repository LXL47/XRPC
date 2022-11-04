package com.xxxx.xrpc.rpc.annota;

import com.xxxx.xrpc.rpc.netty.Client;
import com.xxxx.xrpc.rpc.to.Transfer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: xuepeiquan
 * @Date: 2022/10/13
 * @Time: 18:44
 * @Description
 */
@Slf4j
@Component
@Aspect  //切面    定义通知和切点的关系
public class XClientAspect {

    @Autowired
    private Client client;

    @Pointcut("@annotation(com.xxxx.xrpc.rpc.annota.XClient)")
    public void pt(){}


    // 调用Client类的proxy方法，通过proxy方法调用远程服务端方法，获得返回结果
    @Around("pt()")
    public Object around( ProceedingJoinPoint pjp ){


        // 获取  远程调用方法的相关信息【方法名，参数列表】

        // 方法名
        Signature signature = pjp.getSignature();
        String methodName = signature.getName ();

        // 参数列表
        Object[] list = pjp.getArgs ();

        Transfer transfer = new Transfer (methodName,list);
        log.info ("注解，构成Transfer对象："+transfer);


        // todo 这个函数直接传Object就行，调用这个函数的函数会返回正确的结果，也就是说
        return client.proxy(transfer).getResult ();
    }

}
