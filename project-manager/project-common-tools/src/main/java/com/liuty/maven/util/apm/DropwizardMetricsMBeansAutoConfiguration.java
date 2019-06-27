package com.liuty.maven.util.apm;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.management.MBeanServer;

/**
 * @描述: 基于AOP实现性能数据采集，监控
 *
 * @创建人: Sunny
 * @创建时间: 2019/6/26
 */
@Configuration
@ComponentScan({"com.liuty.maven.util.apm"})
@AutoConfigureAfter(AopAutoConfiguration.class)
public class DropwizardMetricsMBeansAutoConfiguration {

    @Value("${metrics.mbeans.domain.name:com.liuty.maven}")
    public String metricsMBeansDomainName;

    @Autowired
    public MBeanServer mbeanServer;

    @Bean
    public JmxReporter jmxReporter(MetricRegistry metricRegistry) {
        JmxReporter reporter = JmxReporter.forRegistry(metricRegistry)
                .inDomain(metricsMBeansDomainName).registerWith(mbeanServer).build();
        return reporter;
    }

    @Bean
    public MetricRegistry metricRegistry() {
        return new MetricRegistry();
    }
}
