package com.xxxx.xrpc.rpc.to;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xuepeiquan
 * @Date: 2022/10/13
 * @Time: 19:20
 * @Description
 */

// 客户端 传输给服务器的内容
public class Transfer implements Serializable {

    private static final long serialVersionUID = 1L;    //序列化ID 便于反序列化

    // 防伪前缀
    private String pref;

    // 方法名 [最重要,因为是 服务器和客户端判断是不是这个方法的唯一标识][所以不能重写方法]
    private String methodName;

    // 参数列表
    private Object[] params;


    // 返回类型 【不需要，因为注解里面的代理方法返回值为Object，转成其他类型也没用，而且调用被代理方法的方法 得到的结果就是 被代理方法正确返回类型】
    //private String resultType;


    // 返回结果 server端提供
    private Object result;



    public Transfer( String methodName,Object[] params) {
        this.methodName = methodName;
        this.params = params;
    }

    public Transfer(String methodName, Object result ) {
        this.methodName = methodName;
        this.result = result;
    }


    public String getPref() {
        return pref;
    }

    public void setPref( String pref ) {
        this.pref = pref;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName( String methodName ) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams( Object[] params ) {
        this.params = params;
    }

    public Object getResult() {
        return result;
    }

    public void setResult( Object result ) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "pref='" + pref + '\'' +
                ", methodName='" + methodName + '\'' +
                ", params=" + Arrays.toString (params) +
                ", result=" + result +
                '}';
    }
}
