package com.xxxx.xrpc.rpc.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xuepeiquan
 * @Date: 2022/11/4
 * @Time: 16:21
 * @Description
 */
public class ExecutorUtil {

    //定义一个线程池处理任务
    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            5,
            200,
            10,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable> (1000),  //默认是Integer的最大值，直接new内存不够
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()); // 如果不想抛弃，默认运行，使用CallerRunsPolicy

}
