package com.sunny.maven.rpc.loadbalancer.context;

import com.sunny.maven.rpc.protocol.meta.ServiceMeta;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SUNNY
 * @description: 连接数上下文
 * @create: 2023-02-08 16:06
 */
public class ConnectionsContext {

    private static volatile Map<String, Integer> connectionsMap = new ConcurrentHashMap<>();

    public static void add(ServiceMeta serviceMeta) {
        String key = generateKey(serviceMeta);
        Integer value = connectionsMap.get(key);
        if (value == null) {
            value = 0;
        }
        value++;
        connectionsMap.put(key, value);
    }
    public static Integer getValue(ServiceMeta serviceMeta) {
        String key = generateKey(serviceMeta);
        return connectionsMap.get(key);
    }
    private static String generateKey(ServiceMeta serviceMeta) {
        return serviceMeta.getServiceAddr().concat(":").concat(String.valueOf(serviceMeta.getServicePort()));
    }
}
