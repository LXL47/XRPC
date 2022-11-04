package com.xxxx.xrpc.rpc.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: xuepeiquan
 * @Date: 2022/11/4
 * @Time: 13:39
 * @Description
 */
@Slf4j
public class Methods {

    // todo 提供远程服务的方法列表 （不用缓存，因为程序运行的时候就已经确定那些方法标上注解，提供服务了）
    // 工具类在对象初始化时收集方法，放到这里
    public static HashMap<String,Method> methods = new HashMap<> ();

    // 方法对应的对象（用来执行反射）
    public static HashMap<String,Object> objects = new HashMap<> ();

}
