package com.sunny.maven.util.bean;

import net.sf.cglib.beans.BeanCopier;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SUNNY
 * @description: 使用cglib的BeanCopier实现对象复制
 * @create: 2021-08-04 15:35
 */
public class BeanCopyUtils {

    /**
     * BeanCopier的缓存
     */
    private static final ConcurrentHashMap<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();

    /**
     * 私有构造函数
     */
    private BeanCopyUtils() {}

    /**
     * 复制对象
     * @param srcObj 来源对象
     * @param descObj 目标对象
     */
    public static void copy(Object srcObj, Object descObj) {
        String bcKey = genKey(srcObj.getClass(), descObj.getClass());
        BeanCopier bc = BEAN_COPIER_CACHE.get(bcKey);
        if (Objects.isNull(bc)) {
            bc = BeanCopier.create(srcObj.getClass(), descObj.getClass(), false);
            BEAN_COPIER_CACHE.put(bcKey, bc);
        }
        bc.copy(srcObj, descObj, null);
    }

    /**
     * 生成key
     * @param srcClazz 源文件的class
     * @param decsClazz 目标文件的class
     * @return String
     */
    private static String genKey(Class<?> srcClazz, Class<?> decsClazz) {
        return srcClazz.getName() + decsClazz.getName();
    }
}
