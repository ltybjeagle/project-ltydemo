package com.sunny.maven.facade.provider;

import com.sunny.maven.facade.HelloFacade;
import com.sunny.maven.service.HelloService;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author SUNNY
 * @description: 服务提供端(JAX-RS)
 * @create: 2021-09-15 17:22
 */
@RestSchema(schemaId = "service-hello")
@Path("/jax-rs/service")
@Produces(MediaType.APPLICATION_JSON)
public class JaxRsHelloServiceProvider implements HelloFacade {
    private static final Logger logger = LoggerFactory.getLogger(JaxRsHelloServiceProvider.class);
    /**
     * 逻辑对象
     */
    private HelloService helloService;

    /**
     * 请求接口
     * @param name 参数
     * @return 响应
     */
    @Path("/hello")
    @GET
    @Override
    public String sayHello(String name) throws Exception {
        logger.info("进入service.provider(jax-rs), 参数：{}", name);
        String result = helloService.sayHello(name);
        logger.info("service.provider(jax-rs)返回, 结果：{}", result);
        return result;
    }

    /**
     * 构造函数
     * @param helloService 注入服务对象
     */
    public JaxRsHelloServiceProvider(@Autowired HelloService helloService) {
        this.helloService = helloService;
    }
}
