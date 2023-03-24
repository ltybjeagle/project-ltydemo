package com.sunny.maven;

import com.netflix.appinfo.*;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author SUNNY
 * @description: EurekaClientTest
 * @create: 2023-02-14 10:47
 */
@Slf4j
public class EurekaClientTest {

    private ApplicationInfoManager applicationInfoManager;

    public void testEurekaClient() throws InterruptedException {
        Properties properties = new Properties();
        properties.setProperty("eureka.serviceUrl.default", "http://localhost:8761/eureka");
//        properties.setProperty("eureka.vipAddress", "demo-service");
        properties.setProperty("eureka.registration.enabled", "false");
        ConfigurationManager.loadProperties(properties);

        EurekaInstanceConfig instanceConfig = new MyDataCenterInstanceConfig();
        DefaultEurekaClientConfig clientConfig = new DefaultEurekaClientConfig();
        InstanceInfo.InstanceStatus initialStatus = InstanceInfo.InstanceStatus.STARTING;

        InstanceInfo.Builder builder = InstanceInfo.Builder.newBuilder();
        InstanceInfo instanceInfo = builder.setInstanceId("demo-service").
                setVIPAddress("demo-service").
                setStatus(initialStatus).
                add("serviceAddr","127.0.0.1").
                add("servicePort", "27008").
                add("serviceName", "demo-service").
                add("serviceGroup", "SUNNY").
                add("weight", "1").build();

//        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();

        applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
        log.info("Registering service to eureka with status STARTING");
        EurekaClient eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
        // 注册
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);

        Thread.sleep(10000);

        // 获取
        List<InstanceInfo> serverInfos = eurekaClient.getInstancesByVipAddress("demo-service", false);
        for (InstanceInfo nextServerInfo : serverInfos) {
            Map<String, String> metadata = nextServerInfo.getMetadata();
            System.out.println(metadata);
        }
        while (true) {
            Thread.sleep(1000);
        }
//        applicationInfoManager.
        // eurekaClient.getInstancesByVipAddress()
//        instanceConfig.setIpAddress("127.0.0.1");
//        instanceConfig.setPort(27880);
//        instanceConfig.setApplicationName("test-server");
//        instanceConfig.setInstanceId("test-server-1");
        //eurekaClient.getApplicationInfoManager().registerAppMetadata();

    }


}
