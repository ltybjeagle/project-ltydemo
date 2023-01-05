package com.sunny.maven.rpc.provider.common.scanner;

import com.sunny.maven.rpc.annotation.RpcService;
import com.sunny.maven.rpc.common.helper.RpcServiceHelper;
import com.sunny.maven.rpc.common.scanner.ClassScanner;
import com.sunny.maven.rpc.protocol.meta.ServiceMeta;
import com.sunny.maven.rpc.registry.api.RegistryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SUNNY
 * @description: @RpcService注解扫描器
 * @create: 2022-12-23 16:06
 */
public class RpcServiceScanner extends ClassScanner {

    private static final Logger logger = LoggerFactory.getLogger(RpcServiceScanner.class);

    /**
     * 扫描指定包下的类，并筛选使用@RpcService注解标注的类
     * @param scanPackage
     * @return
     * @throws Exception
     */
    public static Map<String, Object>
    doScannerWithRpcServiceAnnotationFilterAndRegistryService(String host, int port, String scanPackage,
                                                              RegistryService registryService)
            throws Exception {
        Map<String, Object> handlerMap = new HashMap<>();
        List<String> classNameList = getClassNameList(scanPackage);
        if (classNameList.isEmpty()) {
            return handlerMap;
        }
        classNameList.forEach(className -> {
            try {
                Class<?> clazz = Class.forName(className);
                RpcService rpcService = clazz.getAnnotation(RpcService.class);
                if (rpcService != null) {
                    // 优先使用interfaceClass，interfaceClass的name为空，在使用interfaceClassName
                    ServiceMeta serviceMeta = new ServiceMeta(getServiceName(rpcService), rpcService.version(), host,
                            port, rpcService.group());
                    // 将元数据注册到注册中心
                    registryService.register(serviceMeta);
                    // handlerMap中的Key先简单存储为serviceName+version+group，后续根据实际情况处理key
                    String key =
                            RpcServiceHelper.buildServiceKey(serviceMeta.getServiceName(),
                                    serviceMeta.getServiceVersion(), serviceMeta.getServiceGroup());
                    handlerMap.put(key, clazz.newInstance());
                }
            } catch (Exception e) {
                logger.error("scan classes throws exception:{}", e.getMessage());
            }
        });
        return handlerMap;
    }

    /**
     * 获取ServiceName
     * @param rpcService
     * @return
     */
    private static String getServiceName(RpcService rpcService) {
        // 优先使用interfaceClass
        Class clazz = rpcService.interfaceClass();
        if (clazz == void.class) {
            return rpcService.interfaceClassName();
        }
        String serviceName = clazz.getName();
        if (serviceName == null || serviceName.trim().isEmpty()) {
            serviceName = rpcService.interfaceClassName();
        }
        return serviceName;
    }
}
