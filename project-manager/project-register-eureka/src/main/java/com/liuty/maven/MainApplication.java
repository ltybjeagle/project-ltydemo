package com.liuty.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Description: 服务注册、发现应用
 *  注册中心服务端：
 *      1、服务端使用双层MAP保存注册信息：
 *          第一层MAP：key:服务名    value:第二层MAP         同一服务多实例集群部署
 *          第二层MAP：key:实例名    value:具体实例信息
 *      2、提供集群高可用支持，存在同步延迟
 *          集群里多个注册中心通过互连（互相配置注册中心服务地址），将信息同步，客户端注册信息到一个节点，
 *          服务节点将注册信息同步到其他节点
 *      3、Eureka注册中心是CAP原理中的AP（可用性、可靠性）
 *
 *  注册中心客户端：
 *      1、服务提供方、消费方，将服务注册到注册中心，消费端获取服务列表缓存到本地，根据负载策略调用服务提供方
 *      2、客户端发起心跳请求，告诉服务端服务是否可用
 *      3、客户端提供三个定时任务：
 *          服务注册定时任务
 *          服务续约定时任务（心跳）
 *          服务列表获取定时任务
 *      4、客户端通过Rest方式调用注册中心服务注册客户端信息，注册的时候传入instanceInfo元数据对象
 *      6、Region和Zone：一对多关系，Zone就是具体的服务节点，没有配置的话获取defaultZone，
 *      即service-url.defaultZone
 *      7、Jersey是JAX-RS的参考实现，XStream用来将对象序列化成XML和返序列化成对象的JAVA类库
 */
@SpringBootApplication
@EnableEurekaServer
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
