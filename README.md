# XRPC

这是我自己写的一个基于netty的rpc框架



###### 项目地址

- github:	https://github.com/LXL47/XRPC
- 码云：     https://gitee.com/xue_peiquan/xrpc





语言:			java

使用环境： SpringBoot





###### 目录结构

- 示例：里面是XRPC 客户端和服务端的例子，可直接运行
  - 运行后，可以通过访问 http://127.0.0.1:7000/1/1 或 http://127.0.0.1:7000/1/2 两个接口查看效果
- 源码：XRPC的源码，带有一定的注释
  - 想了解自定义注解，netty，以及如何在Spring中找到添加了指定注解的方法 的同学可以看看
- XRPC.jar  
- img:  用来存放这个文档的图片







(可以查看下面的说明，也可以直接运行示例，查看里面的todo，todo里对各个文件和配置进行了说明)

###### 使用方式

1.git clone 之后，把XRPC.jar导入项目目录



2.在启动类的@SpringBootApplication加一个扫描的路径，如下：

```java
@SpringBootApplication(scanBasePackages = {"com.test.client","com.xxxx.xrpc.rpc"})
```



3.配置

【注意！本地开启服务的端口不能和项目的端口一样，不然会报错】

```
# todo 设置XRPC  远程服务端地址 端口
# 不设置 开不了本地的Client服务,调用不了远程服务端的函数
#xrpc.remoteHost=localhost
#xrpc.remotePort=7001

# todo 如果要给其他机器提供服务,设置 (是本地提供服务的方法所在的包)
xrpc.classPath=com/test/server

# todo 如果要给其他机器提供服务,设置本地开启服务的端口
xrpc.serverPort=8001

# todo 需要开info级别才能看到XRPC的日志
logging.level.com: info
```



4.在maven中，导入依赖

```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <!-- 排除springboot默认使用的logback  -->
    <exclusions>
    <exclusion>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-logging</artifactId>
    </exclusion>
    </exclusions>
   </dependency>


    <!--todo 加上这个依赖 log4j2 -->
   <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
   </dependency>



    <!--  todo 加上这个依赖  引入本地rpc  ${project.basedir}是项目的根路径-->
   <dependency>
    <groupId>com.xxxx</groupId>
    <artifactId>XRPC</artifactId>
    <version>0.0.1</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/XRPC.jar</systemPath>
   </dependency>
```



5.客户端，如果需要调用某个远程方法，把那个方法的签名复制到一个类中，加上@XClient注解，如下

【这个方法需要方法体，里面怎么写都没问题，是不会执行的】

```
@Component
public class XRPCService {


    @XClient
    // 【这个方法需要方法体，里面怎么写都没问题，是不会执行的】
    public String serverMethod(String s){
        return null;
}
```



6.服务端，要提供某个方法给客户端使用，需要在方法上添加@XServer注解，如下

【注意！这个方法所在的类需要添加到spring里管理，不然调用不了】

【注意！要提供调用的所有方法，方法之间名字不能相同】

【注意！这个方法必须为public】

```
@Component
public class ServerTest {

    @XServer
    public String serverMethod(String s){
        return "服务端收到此消息并回传：" + s;
    }
}
```







###### 效果展示

客户端调用服务端的 serverMethod 方法



1.访问接口   http://127.0.0.1:7000/1/1 或 http://127.0.0.1:7000/1/2,调用  客户端的Controller

![](http://blog.xpqly.love/WD1NCU79SHQ7X%28YLJTV4SF4.png)







2.客户端的Controller调用 XRPCService类里的空方法

(@XCliernt注解进行代理,调用底层netty客户端请求 netty服务端)

![](http://blog.xpqly.love/N0%28ISMZ%7B6DTW~%25YNP0Y%2856M.png)







3.netty服务端使用反射调用添加@XService的方法

![](http://blog.xpqly.love/28%40AFNRDG74ES%7B~_PCG5%60%5BR.png)







4.方法的结果原路返回,到达Controller,然后在浏览器呈现给用户

![](http://blog.xpqly.love/%2497LPQ20%7BT7%7BD%5D4VXA9%5DUMJ.png)

