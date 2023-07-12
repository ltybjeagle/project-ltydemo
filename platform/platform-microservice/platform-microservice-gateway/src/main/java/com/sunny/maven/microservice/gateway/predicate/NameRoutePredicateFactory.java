package com.sunny.maven.microservice.gateway.predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author SUNNY
 * @description: 自定义断言功能
 * @create: 2023/7/12 15:24
 */
@Component
public class NameRoutePredicateFactory
        extends AbstractRoutePredicateFactory<NameRoutePredicateConfig> {
    @Override
    public Predicate<ServerWebExchange> apply(NameRoutePredicateConfig config) {
        return (ServerWebExchange) -> {
            String name = ServerWebExchange.getRequest().getQueryParams().getFirst("name");
            if (StringUtils.isEmpty(name)) {
                name = "";
            }
            return name.equals(config.getName());
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("name");
    }

    public NameRoutePredicateFactory() {
        super(NameRoutePredicateConfig.class);
    }
}
