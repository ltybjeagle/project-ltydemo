package com.sunny.maven.core.proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SUNNY
 * @description: AsmClassLoader
 * @create: 2022-11-20 11:59
 */
public class AsmClassLoader extends ClassLoader {
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
