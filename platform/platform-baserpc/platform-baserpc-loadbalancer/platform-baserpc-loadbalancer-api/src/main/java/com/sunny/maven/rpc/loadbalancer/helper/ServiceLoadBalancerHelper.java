package com.sunny.maven.rpc.loadbalancer.helper;

import com.sunny.maven.rpc.common.utils.CollectionUtils;
import com.sunny.maven.rpc.protocol.meta.ServiceMeta;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author SUNNY
 * @description: 服务负载均衡辅助类
 * @create: 2023-02-08 11:21
 */
public class ServiceLoadBalancerHelper {
    /**
     * 临时记录ServiceInstance<ServiceMeta>列表
     */
    private static volatile List<ServiceInstance<ServiceMeta>> tmpServiceInstances = new ArrayList<>();
    /**
     * 缓存List<ServiceMeta>
     */
    private static volatile Map<String, List<ServiceMeta>> serviceMetaMap = new ConcurrentHashMap<>();
    /**
     * 缓存的Key
     */
    private static final String CACHE_KEY = "cache_key";

    /**
     * 通过List<ServiceInstance<ServiceMeta>>列表获取List<ServiceMeta>
     */
    public static List<ServiceMeta> getServiceMetaList(List<ServiceInstance<ServiceMeta>> serviceInstances) {
        List<ServiceMeta> resultList = null;
        if (CollectionUtils.isEmpty(serviceInstances)) {
            return resultList;
        }
        // 元数据列表有变动
        if (!CollectionUtils.equals(tmpServiceInstances, serviceInstances)) {
            resultList = getServiceMetaListFromChange(serviceInstances);
        } else {
            resultList = getServiceMetaListFromCache(serviceInstances);
        }
        return resultList;
    }

    /**
     * 缓存列表变动
     */
    private static List<ServiceMeta> getServiceMetaListFromChange(List<ServiceInstance<ServiceMeta>> serviceInstances) {
        tmpServiceInstances = serviceInstances;
        List<ServiceMeta> resultList = getServiceMetaListFromInstance(serviceInstances);
        serviceMetaMap.put(CACHE_KEY, resultList);
        return resultList;
    }

    /**
     * 从缓存中获取
     */
    private static List<ServiceMeta> getServiceMetaListFromCache(List<ServiceInstance<ServiceMeta>> serviceInstances) {
        List<ServiceMeta> serviceMetaList = serviceMetaMap.get(CACHE_KEY);
        if (CollectionUtils.isEmpty(serviceMetaList)) {
            serviceMetaList = getServiceMetaListFromInstance(serviceInstances);
            serviceMetaMap.put(CACHE_KEY, serviceMetaList);
        }
        return serviceMetaList;
    }

    /**
     * 数据转换
     */
    private static List<ServiceMeta> getServiceMetaListFromInstance(
            List<ServiceInstance<ServiceMeta>> serviceInstances) {
        List<ServiceMeta> list = new ArrayList<>(serviceInstances.size());
        for (ServiceInstance<ServiceMeta> serviceInstance : serviceInstances) {
            list.add(serviceInstance.getPayload());
        }
        return list;
    }
}
