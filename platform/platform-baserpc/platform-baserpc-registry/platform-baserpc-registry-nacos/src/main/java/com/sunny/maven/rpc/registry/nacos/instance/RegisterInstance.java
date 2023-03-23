package com.sunny.maven.rpc.registry.nacos.instance;

import com.alibaba.nacos.api.naming.pojo.Instance;

/**
 * @author SUNNY
 * @description: Nacos注册实例
 * @create: 2023-03-22 15:53
 */
public class RegisterInstance<T> extends Instance {
    private static final long serialVersionUID = -3187613797976695645L;
    /**
     * 实例对象
     */
    private T payload;
    /**
     * 获取实例对象
     */
    public T getPayload() {
        return payload;
    }
    /**
     * 私有构造函数
     */
    private RegisterInstance() {}

    public static class RegisterInstanceBuilder<T> {
        RegisterInstance<T> registerInstance;
        public RegisterInstanceBuilder(String ip, int port) {
            registerInstance = new RegisterInstance<>();
            registerInstance.setIp(ip);
            registerInstance.setPort(port);
        }

        public RegisterInstanceBuilder<T> payload(T payload) {
            registerInstance.payload = payload;
            return this;
        }

        public RegisterInstance<T> build() {
            return registerInstance;
        }
    }
}
