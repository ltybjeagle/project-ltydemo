package com.liuty.maven.config;

/*
@Configuration
@AutoConfigureAfter(AopAutoConfiguration.class)
*/
public class DropwizardMetricsMBeansAutoConfiguration {
/*
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
    */
}
