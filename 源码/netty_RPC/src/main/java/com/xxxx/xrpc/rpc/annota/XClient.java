package com.xxxx.xrpc.rpc.annota;

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
public @interface XClient {

    String name() default "";
}
