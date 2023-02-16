package com.sunny.maven;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * @author SUNNY
 * @description: EurekaClientTest
 * @create: 2023-02-14 10:47
 */
@Slf4j
public class EurekaClientTest {

    public void testEurekaClient() {
        Properties properties = new Properties();
        properties.setProperty("eureka.client.refresh.interval", "5");
        properties.setProperty("eureka.serviceUrl.default", "http://localhost:8761/eureka");
        properties.setProperty("eureka.metadata.weight", "1");
        properties.setProperty("eureka.registration.enabled", "false");
        ConfigurationManager.loadProperties(properties);

        // 初始化应用信息管理器，设置其状态为STARTING
        CustomEurekaInstanceConfig instanceConfig = new CustomEurekaInstanceConfig();
        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        ApplicationInfoManager applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
        log.info("Registering service to eureka with status STARTING");
        EurekaClient eurekaClient = new DiscoveryClient(applicationInfoManager, new DefaultEurekaClientConfig());
        // eurekaClient.getInstancesByVipAddress()
        instanceConfig.setIpAddress("127.0.0.1");
        instanceConfig.setPort(27880);
        instanceConfig.setApplicationName("test-server");
        instanceConfig.setInstanceId("test-server-1");
        //eurekaClient.getApplicationInfoManager().registerAppMetadata();

    }
}
