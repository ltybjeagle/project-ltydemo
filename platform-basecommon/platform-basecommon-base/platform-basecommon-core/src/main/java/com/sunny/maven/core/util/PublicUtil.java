package com.sunny.maven.core.util;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * @author SUNNY
 * @description: 公共工具类
 * @create: 2022-01-14 23:53
 */
public class PublicUtil {
    /**
     * The constant STRING_NULL.
     */
    private final static String STRING_NULL = "-";
    /**
     * 匹配手机号码, 支持+86和86开头
     */
    private static final String REGX_MOBILENUM = "^((\\+86)|(86))?(13|15|17|18)\\d{9}$";

    /**
     * 匹配邮箱帐号
     */
    private static final String REGX_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

    /**
     * 判断对象是否Empty(null或元素为0)
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     * @param pObj 待检查对象
     * @return boolean 返回的布尔值
     */
    public static boolean isEmpty(Object pObj) {
        if (pObj == null) {
            return true;
        }
        if (pObj == "") {
            return true;
        }
        if (pObj instanceof String) {
            return ((String) pObj).length() == 0;
        } else if (pObj instanceof Collection) {
            return ((Collection) pObj).isEmpty();
        } else if (pObj instanceof Map) {
            return ((Map) pObj).size() == 0;
        }
        return false;
    }

    /**
     * 判断对象是否为NotEmpty(!null或元素大于0)
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     * @param pObj 待检查对象
     * @return boolean 返回的布尔值
     */
    public static boolean isNotEmpty(Object pObj) {
        if (pObj == null) {
            return false;
        }
        if (pObj == "") {
            return false;
        }
        if (pObj instanceof String) {
            return ((String) pObj).length() != 0;
        } else if (pObj instanceof Collection) {
            return !((Collection) pObj).isEmpty();
        } else if (pObj instanceof Map) {
            return ((Map) pObj).size() != 0;
        }
        return true;
    }

    /**
     * 匹配手机号码（先支持13, 15, 17, 18开头的手机号码）.
     * @param inputStr the input str
     * @return the boolean
     */
    public static Boolean isMobileNumber(String inputStr) {
        return !isNull(inputStr) && inputStr.matches(REGX_MOBILENUM);
    }

    /**
     * 判断一个或多个对象是否为空
     * @param values 可变参数, 要判断的一个或多个对象
     * @return 只有要判断的一个对象都为空则返回true, 否则返回false boolean
     */
    public static boolean isNull(Object... values) {
        if (!isNotNullAndNotEmpty(values)) {
            return true;
        }
        for (Object value : values) {
            boolean flag;
            if (value instanceof Object[]) {
                flag = !isNotNullAndNotEmpty((Object[]) value);
            } else if (value instanceof Collection<?>) {
                flag = !isNotNullAndNotEmpty((Collection<?>) value);
            } else if (value instanceof String) {
                flag = isOEmptyOrNull(value);
            } else {
                flag = (null == value);
            }
            if (flag) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断Object是否为空
     * @param o the o
     * @return boolean boolean
     */
    private static boolean isOEmptyOrNull(Object o) {
        return o == null || isSEmptyOrNull(o.toString());
    }

    /**
     * 判断String是否为空
     * @param s the s
     * @return boolean boolean
     */
    private static boolean isSEmptyOrNull(String s) {
        return trimAndNullAsEmpty(s).length() <= 0;
    }

    /**
     * 剔除空格.
     * @param s the s
     * @return java.lang.String string
     */
    private static String trimAndNullAsEmpty(String s) {
        if (s != null && !s.trim().equals(STRING_NULL)) {
            return s.trim();
        } else {
            return "";
        }
    }

    /**
     * 判断对象数组是否为空并且数量大于0
     * @param value the value
     * @return boolean
     */
    private static Boolean isNotNullAndNotEmpty(Object[] value) {
        boolean bl = false;
        if (null != value && 0 < value.length) {
            bl = true;
        }
        return bl;
    }

    /**
     * 判断对象集合（List,Set）是否为空并且数量大于0
     * @param value the value
     * @return boolean
     */
    private static Boolean isNotNullAndNotEmpty(Collection<?> value) {
        boolean bl = false;
        if (null != value && !value.isEmpty()) {
            bl = true;
        }
        return bl;
    }

    /**
     * 正则校验是否邮箱
     * @param str the str
     * @return the boolean
     */
    public static boolean isEmail(String str) {
        boolean bl = true;
        if (isSEmptyOrNull(str) || !str.matches(REGX_EMAIL)) {
            bl = false;
        }
        return bl;
    }

    /**
     * Uuid string.
     * @return the string
     */
    public synchronized static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 私有构造函数
     */
    private PublicUtil() {}
}
