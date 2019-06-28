package com.liuty.maven.config;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.rpc.service.EchoService;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

/**
 * @描述: Dubbo接口服务检测
 *
 * @创建人: Sunny
 * @创建时间: 2019/6/28
 */
public class DubboHealthIndicator extends AbstractHealthIndicator {
    private final ReferenceBean bean;

    public DubboHealthIndicator(ReferenceBean bean) {
        this.bean = bean;
    }
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.withDetail("interface", bean.getObjectType());
        final EchoService service = (EchoService) bean.getObject();
        service.$echo("hi");
        builder.up();
    }
}
