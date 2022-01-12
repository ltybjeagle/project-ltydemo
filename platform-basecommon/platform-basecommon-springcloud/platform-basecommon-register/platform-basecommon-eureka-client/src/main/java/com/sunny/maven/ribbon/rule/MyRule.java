package com.sunny.maven.ribbon.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * @author SUNNY
 * @description: 自定义负载策略
 * @create: 2021-12-09 11:10
 */
public class MyRule extends AbstractLoadBalancerRule {

    @Override
    public Server choose(Object o) {
        List<Server> servers = this.getLoadBalancer().getAllServers();
        servers.stream().forEach(server -> System.out.println(server.getHostPort()));
        return servers.get(0);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }
}
