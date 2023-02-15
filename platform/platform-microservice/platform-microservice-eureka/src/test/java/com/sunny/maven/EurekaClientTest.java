package com.sunny.maven;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SUNNY
 * @description: EurekaClientTest
 * @create: 2023-02-14 10:47
 */
@Slf4j
public class EurekaClientTest {

    public void testEurekaClient() {
        // 初始化应用信息管理器，设置其状态为STARTING
        EurekaInstanceConfig instanceConfig = new MyDataCenterInstanceConfig();
        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        ApplicationInfoManager applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
        log.info("Registering service to eureka with status STARTING");
        EurekaClient eurekaClient = new DiscoveryClient(applicationInfoManager, new DefaultEurekaClientConfig());
        //DynamicPropertyFactory configInstance = DynamicPropertyFactory.getInstance();
    }
}
