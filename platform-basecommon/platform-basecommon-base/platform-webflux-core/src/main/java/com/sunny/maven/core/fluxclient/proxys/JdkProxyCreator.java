package com.sunny.maven.core.fluxclient.proxys;

import com.sunny.maven.core.fluxclient.annotation.ApiServer;
import com.sunny.maven.core.fluxclient.beans.MethodInfo;
import com.sunny.maven.core.fluxclient.beans.ServerInfo;
import com.sunny.maven.core.fluxclient.interfaces.ProxyCreator;
import com.sunny.maven.core.fluxclient.interfaces.RestHandler;
import com.sunny.maven.core.fluxclient.resthandlers.WebClientRestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author SUNNY
 * @description: 用JDK动态代理实现代理类
 * @create: 2022-09-30 10:37
 */
@Slf4j
public class JdkProxyCreator implements ProxyCreator {
    @Override
    public Object createProxy(Class<?> type) {
        log.info("createProxy: {}", type);
        // 根据接口得到API服务器
        ServerInfo serverInfo = extractServerInfo(type);
        log.info("serverInfo: {}", serverInfo);
        // 给每一个代理类创建一个实例
        RestHandler handler = new WebClientRestHandler();
        // 初始化服务器信息（初始化WebClient）
        handler.init(serverInfo);
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{type}, (proxy, method, args) -> {
            // 根据方法定义和调用参数得到调用的相关信息
            MethodInfo methodInfo = extractMethodInfo(method, args);
            log.info("methodInfo: {}", methodInfo);
            // 调用rest
            return handler.invokeRest(methodInfo);
        });
    }

    /**
     * 根据方法定义和调用参数得到调用的相关信息
     * @param method
     * @param args
     * @return
     */
    private MethodInfo extractMethodInfo(Method method, Object[] args) {
        MethodInfo methodInfo = new MethodInfo();
        extractUrlAndMethod(method, methodInfo);
        extractRequestParamAndBody(method, args, methodInfo);

        // 提取返回对象的信息
        extractReturnInfo(method, methodInfo);
        return methodInfo;
    }

    /**
     * 提取返回对象的信息
     * @param method
     * @param methodInfo
     */
    private void extractReturnInfo(Method method, MethodInfo methodInfo) {
        /*
         * 返回Flux还是Mono
         * isAssignableFrom判断类型是否是某个类的子类
         * instanceof判断实例是否是某个类的子类
         */
        boolean isFlux = method.getReturnType().isAssignableFrom(Flux.class);
        methodInfo.setReturnFlux(isFlux);

        // 得到返回对象的实际类型
        Class<?> elementType = extractElementType(method.getGenericReturnType());
        methodInfo.setReturnElementType(elementType);
    }

    /**
     * 得到请求的param和body
     * @param method
     * @param args
     * @param methodInfo
     */
    private void extractRequestParamAndBody(Method method, Object[] args, MethodInfo methodInfo) {
        // 得到调用的参数和body
        Parameter[] parameters = method.getParameters();
        // 参数和值对应的map
        Map<String, Object> params = new LinkedHashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            // 是否带@PathVariable注解
            PathVariable annPath = parameters[i].getAnnotation(PathVariable.class);
            if (annPath != null) {
                params.put(annPath.value(), args[i]);
            }
            // 是否带@RequestBody注解
            RequestBody annBody = parameters[i].getAnnotation(RequestBody.class);
            if (annBody != null) {
                methodInfo.setBody((Mono<?>) args[i]);
                methodInfo.setBodyElementType(extractElementType(parameters[i].getParameterizedType()));
            }
        }
        methodInfo.setParams(params);
    }

    /**
     * 得到实际类型
     * @param genericReturnType
     * @return
     */
    private Class<?> extractElementType(Type genericReturnType) {
        Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
        return (Class<?>) actualTypeArguments[0];
    }

    /**
     * 得到请求的URL和方法
     * @param method
     * @param methodInfo
     */
    private void extractUrlAndMethod(Method method, MethodInfo methodInfo) {
        // 得到请求的URL和请求方法
        Annotation[] annotations = method.getAnnotations();
        Stream.of(annotations).forEach(annotation -> {
            // GET
            if (annotation instanceof GetMapping) {
                GetMapping ann = (GetMapping) annotation;
                methodInfo.setUrl(ann.value()[0]);
                methodInfo.setMethod(HttpMethod.GET);
            }
            // POST
            else if (annotation instanceof PostMapping) {
                PostMapping ann = (PostMapping) annotation;
                methodInfo.setUrl(ann.value()[0]);
                methodInfo.setMethod(HttpMethod.POST);
            }
            // DELETE
            else if (annotation instanceof DeleteMapping) {
                DeleteMapping ann = (DeleteMapping) annotation;
                methodInfo.setUrl(ann.value()[0]);
                methodInfo.setMethod(HttpMethod.DELETE);
            }
            // PUT
            else if (annotation instanceof PutMapping) {
                PutMapping ann = (PutMapping) annotation;
                methodInfo.setUrl(ann.value()[0]);
                methodInfo.setMethod(HttpMethod.PUT);
            }
        });
    }

    /**
     * 提取服务器信息
     * @param type
     * @return
     */
    private ServerInfo extractServerInfo(Class<?> type) {
        ServerInfo serverInfo = new ServerInfo();
        ApiServer annotation = type.getAnnotation(ApiServer.class);
        serverInfo.setUrl(annotation.value());
        return serverInfo;
    }
}
