package com.liuty.maven.config;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.management.MBeanServer;

@Configuration
@AutoConfigureAfter(AopAutoConfiguration.class)
public class DropwizardMetricsMBeansAutoConfiguration {

    @Value("${metrics.mbeans.domain.name:com.liuty.maven}")
    public String metricsMBeansDomainName;

    @Autowired
    public MBeanServer mbeanServer;

    @Autowired
    public MetricRegistry metricRegistry;

    @Bean
    public JmxReporter jmxReporter() {
        JmxReporter reporter = JmxReporter.forRegistry(metricRegistry)
                .inDomain(metricsMBeansDomainName).registerWith(mbeanServer).build();
        return reporter;
    }
}
