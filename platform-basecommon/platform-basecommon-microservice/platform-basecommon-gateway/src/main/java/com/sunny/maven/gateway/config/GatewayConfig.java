package com.sunny.maven.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author SUNNY
 * @description: 网关配置类
 * @create: 2022-07-01 14:09
 */
@Configuration
public class GatewayConfig {
    private final List<ViewResolver> viewResolvers;

    private final ServerCodecConfigurer serverCodecConfigurer;

    @Value("${spring.cloud.gateway.discovery.locator.route-id-prefix}")
    private String routeIdPrefix;

    public GatewayConfig(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                         ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    /**
     * 初始化一个限流的过滤器
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    @PostConstruct
    public void init() {
        // route维度的限流
        this.initGatewayRules();
//        // 自定义API分组流控
//        this.initCustomizedApis();
        this.initBlockHandlers();
    }

    /**
     * 配置初始化的限流参数
     */
    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();

        /*
         * Sentinel整合SpringCloud Gateway使用的API类型为Route ID类型，也就是基于route维度时，
         * 由于Sentinel为SpringCloud Gateway网关生成的API名称规则如下：
         * 生成的规则为：${spring.cloud.gateway.discovery.locator.route-id-prefix}后面直接加上目标微服务的名称，如下所示。
         * ${spring.cloud.gateway.discovery.locator.route-id-prefix}目标微服务的名称
         * 其中，${spring.cloud.gateway.discovery.locator.route-id-prefix}是在yml文件中配置的访问前缀
         *
         * 为了让通过服务网关访问目标微服务链接后，请求链路中生成的API名称与流控规则中生成的API名称一致，
         * 以达到启动项目即可实现访问链接的限流效果，
         * 而无需登录Setinel管理界面手动配置限流规则，可以将
         * resource参数设置为${spring.cloud.gateway.discovery.locator.route-id-prefix}目标微服务的名称
         *
         * 当然，如果不按照上述配置，也可以在项目启动后，通过服务网关访问目标微服务链接后，
         * 在Sentinel管理界面的请求链路中找到对应的API名称所代表的请求链路，
         * 然后手动配置限流规则。
         */
        // 用户微服务网关
        rules.add(this.getGatewayFlowRule(getResource("server-user")));
        // 商品微服务网关
        rules.add(this.getGatewayFlowRule(getResource("server-data")));
        // 订单微服务网关
        rules.add(this.getGatewayFlowRule(getResource("server-camunda")));
        // 加载规则
        GatewayRuleManager.loadRules(rules);
    }

    private String getResource(String targetServiceName) {
        if (routeIdPrefix == null){
            routeIdPrefix = "";
        }
        return routeIdPrefix.concat(targetServiceName);
    }

    private GatewayFlowRule getGatewayFlowRule(String resource){
        // 传入资源名称生成GatewayFlowRule
        GatewayFlowRule gatewayFlowRule = new GatewayFlowRule(resource);
        // 限流阈值
        gatewayFlowRule.setCount(1);
        // 统计的时间窗口，单位为
        gatewayFlowRule.setIntervalSec(1);
        return gatewayFlowRule;
    }

    /**
     * 配置限流的异常处理器
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    /**
     * 自定义限流异常页面
     */
    private void initBlockHandlers() {
        BlockRequestHandler blockRequestHandler = (serverWebExchange, throwable) -> {
            Map<String, Object> map = new HashMap<>(2);
            map.put("code", 1001);
            map.put("codeMsg", "接口被限流了");
            return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).
                    body(BodyInserters.fromObject(map));
        };
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }

    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();
        ApiDefinition api1 = new ApiDefinition("user_api1")
                .setPredicateItems(new HashSet<>() {
                    private static final long serialVersionUID = -4168420171123678873L;

                    {
                    // 以/server-user/user/api1 开头的请求
                    add(new ApiPathPredicateItem().setPattern("/server-user/user/api1/**").
                            setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                }});
        ApiDefinition api2 = new ApiDefinition("user_api2")
                .setPredicateItems(new HashSet<>() {
                    private static final long serialVersionUID = -1153779773743629296L;

                    {
                    // 以/server-user/user/api2/demo1 完成的url路径匹配
                    add(new ApiPathPredicateItem().setPattern("/server-user/user/api2/demo1"));
                }});
        definitions.add(api1);
        definitions.add(api2);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }
}
