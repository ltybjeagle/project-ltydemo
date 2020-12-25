package com.sunny.maven.rmrpc.server;

import com.sunny.maven.rmrpc.annotation.RmRpcService;
import com.sunny.maven.rmrpc.common.RmRpcRequest;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/3/18 10:48
 * @description：服务提供者容器
 * @modified By：
 * @version: 1.0.0
 */
public class RmRpcServiceContainer {
    // 获取所有rpc服务类，即发布服务
    private static final Map<String, Object> services = new HashMap<>();

    public Object getRmRpcService(RmRpcRequest request) {
        return services.get(request.getClassName());
    }

    /**
     * 初始化所有rpc服务类
     * @param clazz 服务类所在包名，多个用英文逗号隔开
     */
    public void init(String clazz) {
        try {
            // 获取所有服务类
            String[] cls = clazz.split(",");
            List<Class<?>> classes = new ArrayList<>();
            for (String cl : cls) {
                List<Class<?>> classList = getClasses(cl);
                classes.addAll(classList);
            }
            // 循环实例化
            for (Class<?> cla : classes) {
                Object obj = cla.newInstance();
                services.put(cla.getAnnotation(RmRpcService.class).value().getName(), obj);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取包下所有有@RpcSercive注解的类
     * @param pkgName
     * @return
     * @throws ClassNotFoundException
     */
    private List<Class<?>> getClasses(String pkgName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        File directory = null;
        try {
            ClassLoader cld = Thread.currentThread().getContextClassLoader();
            if (cld == null) {
                throw new ClassNotFoundException("Can't get class loader.");
            }
            String path = pkgName.replace('.', '/');
            URL resource = cld.getResource(path);
            if (resource == null) {
                throw new ClassNotFoundException("No resource for " + path);
            }
            directory = new File(resource.getFile());
        } catch (NullPointerException x) {
            throw new ClassNotFoundException(pkgName + " (" + directory + ") does not appear to be a valid package a");
        }
        if (directory.exists()) {
            // 获取所有文件
            String[] files = directory.list();
            File[] fileList = directory.listFiles();
            if (fileList != null) {
                for (int i = 0, j = fileList.length; i < j; i++) {
                    File file = fileList[i];
                    // 判断是否是Class文件
                    if (file.isFile() && file.getName().endsWith(".class")) {
                        Class<?> clazz = Class.forName(pkgName + '.' + files[i].substring(0, files[i].length() - 6));
                        if (clazz.getAnnotation(RmRpcService.class) != null) {
                            classes.add(clazz);
                        }
                    } else if (file.isDirectory()) { // 如果是目录，递归查找
                        List<Class<?>> result = getClasses(pkgName + "." + file.getName());
                        if (result != null && !result.isEmpty()) {
                            classes.addAll(result);
                        }
                    }
                }
            }
        } else {
            throw new ClassNotFoundException(pkgName + " does not appear to be a valid package b");
        }
        return classes;
    }

    public static final RmRpcServiceContainer getInstance() {
        return RmRpcServiceContainerHolder.rmRpcServiceContainer;
    }

    private static class RmRpcServiceContainerHolder {
        private static final RmRpcServiceContainer rmRpcServiceContainer = new RmRpcServiceContainer();
    }

    private RmRpcServiceContainer() {
    }
}
