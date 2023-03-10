package com.sunny.maven.rpc.common.scanner.reference;

import com.sunny.maven.rpc.annotation.RpcReference;
import com.sunny.maven.rpc.common.scanner.ClassScanner;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author SUNNY
 * @description: @RpcReference注解扫描器
 * @create: 2022-12-23 16:58
 */
@Slf4j
public class RpcReferenceScanner extends ClassScanner {

    /**
     * 扫描指定包下的类，并筛选使用@RpcReference注解标注的类
     * @param scanPackage
     * @return
     * @throws Exception
     */
    public static Map<String, Object> doScannerWithRpcReferenceAnnotationFilter(String scanPackage) throws Exception {
        Map<String, Object> handlerMap = new HashMap<>();
        List<String> classNameList = getClassNameList(scanPackage, true);
        if (classNameList.isEmpty()) {
            return handlerMap;
        }
        classNameList.forEach(className -> {
            try {
                Class<?> clazz = Class.forName(className);
                Field[] declaredFields = clazz.getDeclaredFields();
                Stream.of(declaredFields).forEach(field -> {
                    RpcReference rpcReference = field.getAnnotation(RpcReference.class);
                    if (rpcReference != null) {
                        // TODO 后续处理逻辑，将@RpcReference注解标注的接口引用代理对象，放入全局缓存中
                        log.info("当前标注了@RpcReference注解的字段名称===>>> {}", field.getName());
                        log.info("@RpcReference注解上标注的属性信息如下：");
                        log.info("version===>>> {}", rpcReference.version());
                        log.info("group===>>> {}", rpcReference.group());
                        log.info("registryType===>>> {}", rpcReference.registryType());
                        log.info("registryAddress===>>> {}", rpcReference.registryAddress());
                    }
                });
            } catch (Exception e) {
                log.error("scan classes throws exception:{}", e.getMessage());
            }
        });
        return handlerMap;
    }
}
