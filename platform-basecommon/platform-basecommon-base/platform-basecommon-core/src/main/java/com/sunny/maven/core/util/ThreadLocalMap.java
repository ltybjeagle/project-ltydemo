package com.sunny.maven.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SUNNY
 * @description: 线程私有变量副本
 * @create: 2022-01-13 23:24
 */
public class ThreadLocalMap {
    /**
     * The constant threadContext.
     */
    private final static ThreadLocal<Map<String, Object>> THREAD_CONTEXT = new MapThreadLocal();

    /**
     * Put.
     * @param key   the key
     * @param value the value
     */
    public static void put(String key, Object value) {
        getContextMap().put(key, value);
    }

    /**
     * Remove object.
     * @param key the key
     * @return the object
     */
    public static Object remove(String key) {
        return getContextMap().remove(key);
    }

    /**
     * Get object.
     * @param key the key
     * @return the object
     */
    public static Object get(String key) {
        return getContextMap().get(key);
    }

    /**
     * 取得thread context Map的实例。
     * @return thread context Map的实例
     */
    private static Map<String, Object> getContextMap() {
        return THREAD_CONTEXT.get();
    }

    /**
     * 清理线程所有被hold住的对象。以便重用！
     */
    public static void remove() {
        getContextMap().clear();
    }

    private static class MapThreadLocal extends ThreadLocal<Map<String, Object>> {
        /**
         * Initial value map.
         * @return the map
         */
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>(8) {
                private static final long serialVersionUID = -3683668199000645542L;
                @Override
                public Object put(String key, Object value) {
                    return super.put(key, value);
                }
            };
        }
    }

    private ThreadLocalMap() {}
}
