package com.sunny.maven.rpc.proxy.asm.classloader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SUNNY
 * @description: 自定义类加载器
 * @create: 2023-01-16 14:16
 */
public class ASMClassLoader extends ClassLoader {

    private final Map<String, byte[]> classMap = new HashMap<>();

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (classMap.containsKey(name)) {
            byte[] bytes = classMap.get(name);
            classMap.remove(name);
            return defineClass(name, bytes, 0, bytes.length);
        }
        return super.findClass(name);
    }

    public void add(String name, byte[] bytes) {
        classMap.put(name, bytes);
    }
}
