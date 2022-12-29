package com.sunny.maven.rpc.common.scanner.server;

import com.sunny.maven.rpc.annotation.RpcService;
import com.sunny.maven.rpc.common.helper.RpcServiceHelper;
import com.sunny.maven.rpc.common.scanner.ClassScanner;
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
    public static Map<String, Object> doScannerWithRpcServiceAnnotationFilterAndRegistryService(String scanPackage)
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
                    // TODO 后续逻辑向注册中心注册服务元数据，同时向handlerMap中记录标注了RpcService注解的类实例
                    // handlerMap中的Key先简单存储为serviceName+version+group，后续根据实际情况处理key
                    String serviceName = getServiceName(rpcService);
                    String key =
                            RpcServiceHelper.buildServiceKey(serviceName, rpcService.version(), rpcService.group());
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
