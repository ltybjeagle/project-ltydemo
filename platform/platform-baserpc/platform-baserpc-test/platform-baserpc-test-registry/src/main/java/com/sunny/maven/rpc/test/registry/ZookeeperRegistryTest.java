package com.sunny.maven.rpc.test.registry;

import com.sunny.maven.rpc.protocol.meta.ServiceMeta;
import com.sunny.maven.rpc.registry.api.RegistryService;
import com.sunny.maven.rpc.registry.api.config.RegistryConfig;
import com.sunny.maven.rpc.registry.zookeeper.ZookeeperRegistryService;
import org.junit.Before;
import org.junit.Test;

/**
 * @author SUNNY
 * @description: 注册测试
 * @create: 2023-01-03 22:43
 */
public class ZookeeperRegistryTest {
    private RegistryService registryService;
    private ServiceMeta serviceMeta;

    @Before
    public void init() throws Exception {
        RegistryConfig registryConfig = new RegistryConfig("127.0.0.1:2181", "zookeeper",
                "random");
        this.registryService = new ZookeeperRegistryService();
        this.registryService.init(registryConfig);
        this.serviceMeta = new ServiceMeta(ZookeeperRegistryTest.class.getName(), "1.0.0",
                "127.0.0.1", 8080, "SUNNY", 0);
    }

    @Test
    public void testRegister() throws Exception {
        this.registryService.register(serviceMeta);
    }

    @Test
    public void testUnRegister() throws Exception {
        this.registryService.unRegister(serviceMeta);
    }

    @Test
    public void testDiscovery() throws Exception {
        this.registryService.discovery(RegistryService.class.getName(), "SUNNY".hashCode(), "localhost");
    }

    @Test
    public void testDestroy() throws Exception {
        this.registryService.destroy();
    }

}
