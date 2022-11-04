package com.xxxx.xrpc.rpc.util;

import com.xxxx.xrpc.rpc.annota.XServer;
import com.xxxx.xrpc.rpc.config.XConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author weiwenbin
 * @Date 2020/5/14 下午5:31
 */
@Slf4j
@Component
public class AnnotationUtil {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private XConfig xConfig;


    // 把带有@XServer注解的方法和它所在的类 放到 Methods类里面
    public AnnotationUtil() {

        new Thread (new Runnable () {
            @Override
            public void run() {

                try {
                    Thread.sleep (3000);
                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }
                try {
                    getAllAddTagAnnotationUrl ();
                } catch (Exception e) {
                    e.printStackTrace ();
                }

                log.info ("带注解方法列表:"+Methods.methods.toString ());
            }
        }).start ();
    }

    /**
     * 获取指定包下所有添加了执行注解的方法信息
     * @param
     * @return
     * @throws Exception
     */
    public void getAllAddTagAnnotationUrl() throws Exception {
        String classPath = xConfig.getClassPath ();
        Class tagAnnotationClass = XServer.class;

        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        MetadataReaderFactory metaReader = new CachingMetadataReaderFactory(resourceLoader);
        Resource[] resources = resolver.getResources("classpath*:"+classPath+"/**/*.class");

        for (Resource r : resources) {
            MetadataReader reader = metaReader.getMetadataReader (r);
            resolveClass(reader, tagAnnotationClass);
        }

    }

    private void resolveClass(
            MetadataReader reader, Class tagAnnotationClass)
            throws Exception {
        String tagAnnotationClassCanonicalName = tagAnnotationClass.getCanonicalName();
        //获取注解元数据
        AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();

        //获取当前类中已添加要扫描注解的方法
        Set<MethodMetadata> annotatedMethods = annotationMetadata.getAnnotatedMethods(tagAnnotationClassCanonicalName);

        // 如果没有这个注解就直接退出
        if(annotatedMethods.size () == 0) return ;

        // 通过反射获得class对象，再获得类的所有方法
        Class clazz = Class.forName (annotationMetadata.getClassName ());

        Method[] methods = clazz.getMethods ();

        // 然后按照方法名一一匹配
        for (MethodMetadata m : annotatedMethods){
            for (Method method : methods) {
                String methodName = method.getName ();

                if (methodName.equals (m.getMethodName ())) {

                    // todo 把反射得到的方法放进去
                    Methods.methods.put (methodName,method);
                    // 使用BeanUtils获取spring里的对象，然后把对象放到Methods类里面，让serverHandlers调用
                    Methods.objects.put (methodName,BeanUtils.getBean (clazz));

                    break;
                }
            }
        }


    }



}