package com.sunny.maven.rmrpc.client;

import com.sunny.maven.rmrpc.common.RmRpcRequest;
import com.sunny.maven.rmrpc.constant.RmRpcConstant;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/3/9 20:38
 * @description：客户端服务类代理工厂
 *  1. 调用getProxy方法获取代理对象
 *  2. 代理对象的方法被调用时会被invoke方法拦截，执行invoke
 *      1）封装参数，用于发送到服务器，定位服务、执行服务
 *      2）链接服务器调用服务
 * @modified By：
 * @version: 1.0.0
 */
public class RmRpcClientProxy implements InvocationHandler {
    private RmRpcClientFactory clientFactory;
    public RmRpcClientProxy(String host, int port, String clientEnum){
        clientFactory = RmRpcClientFactory.getInstance();
        clientFactory.init(host, port, clientEnum);
    }

    /**
     * 生成代理对象
     * @param clazz 代理类型（接口）
     * @return
     */
    public <T> T getProxy(Class<?> clazz) {
        // clazz不是接口不能使用JDK动态代理
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
        // toString方法反射调用处理
        if (RmRpcConstant.OBJECT_INVOKE_METHOD_TOSTRING.equals(method.getName())) {
            return toString();
        }
        // 封装参数
        RmRpcRequest request = new RmRpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParamTypes(method.getParameterTypes());
        request.setParams(params);
        // 链接服务器调用服务
        RmRpcClient client = clientFactory.getRmRpcClient();
        client.setRequest(request);
        return client.start();
    }

    @Override
    public String toString() {
        return "RmRpcClientProxy{" +
                "clientFactory=" + clientFactory +
                '}';
    }
}
