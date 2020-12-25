package com.sunny.maven.rmrpc.config;

import com.sunny.maven.rmrpc.client.RmRpcClientProxy;
import com.sunny.maven.rmrpc.enums.RmRpcEnum;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/3/21 21:06
 * @description：引用配置类
 * @modified By：
 * @version: 1.0.0
 */
public class ReferenceConfig<T> {
    private Class<?> interfaceClass;
    private transient volatile T ref;

    public synchronized T get() {
        if (ref == null) {
            init();
        }
        return ref;
    }

    private void init() {
        RmRpcClientProxy proxy = new RmRpcClientProxy("127.0.0.1",9998,
                RmRpcEnum.ASYNC_SERVER.getServerType());
        ref = proxy.getProxy(interfaceClass);
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
}
