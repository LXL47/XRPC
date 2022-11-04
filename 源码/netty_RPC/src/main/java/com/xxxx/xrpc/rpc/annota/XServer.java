package com.xxxx.xrpc.rpc.annota;

import org.springframework.context.annotation.Bean;

import java.lang.annotation.*;

/**
 * @Author: xuepeiquan
 * @Date: 2022/10/13
 * @Time: 17:19
 * @Description
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XServer {
}
