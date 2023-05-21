package com.sunny.maven.core.exception.extassert;

import com.sunny.maven.core.exception.AppException;

/**
 * @author SUNNY
 * @description: 自定义断言
 * @create: 2023-04-20 22:33
 */
public interface Assert {
    /**
     * 创建异常
     * @param args
     * @return
     */
    AppException newException(Object... args);

    /**
     * 创建异常
     * @param t
     * @param args
     * @return
     */
    AppException newException(Throwable t, Object... args);

    /**
     * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
     * @param obj 待判断对象
     */
    default void assertNotNull(Object obj) {
        if (obj == null) {
            throw newException((Object) null);
        }
    }

    /**
     * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
     * <p>异常信息<code>message</code>支持传递参数方式，避免在判断之前进行字符串拼接操作
     * @param obj
     * @param args
     */
    default void assertNotNull(Object obj, Object... args) {
        if (obj == null) {
            throw newException(args);
        }
    }
}
