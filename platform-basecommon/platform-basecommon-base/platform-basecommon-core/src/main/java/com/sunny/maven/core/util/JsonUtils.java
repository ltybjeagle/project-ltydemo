package com.sunny.maven.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;

/**
 * @author SUNNY
 * @description: Json工具
 * @create: 2022-01-14 23:39
 */
public class JsonUtils {
    /**
     * 对象转化为json字符串
     * @param obj 待转化对象
     * @return 代表该对象的Json字符串
     */
    public static String toJson(final Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 对象转化为json字符串
     * @param obj 待转化对象
     * @param features json特征，为null忽略
     * @return 代表该对象的Json字符串
     */
    public static String toJson(final Object obj, SerializerFeature ...features) {
        return JSON.toJSONString(obj, features);
    }

    /**
     * 对象转化为json字符串并格式化
     * @param obj 待转化对象
     * @param format 是否要格式化
     * @return 代表该对象的Json字符串
     */
    public static String toJson(final Object obj, final boolean format) {
        return JSON.toJSONString(obj, format);
    }

    /**
     * 对象对指定字段进行过滤处理，生成json字符串
     * @param obj 待转化对象
     * @param fields 过滤处理字段
     * @param ignore true做忽略处理，false做包含处理
     * @param features json特征，为null忽略
     * @return 代表该对象的Json字符串
     */
    public static String toJson(final Object obj, final String[] fields, final boolean ignore,
                                SerializerFeature ...features) {
        if (fields == null || fields.length < 1) {
            return toJson(obj);
        }
        if (features == null) {
            features = new SerializerFeature[] { SerializerFeature.QuoteFieldNames };
        }
        return JSON.toJSONString(obj, (PropertyFilter) (object, name, value) -> {
            for (String field : fields) {
                if (name.equals(field)) {
                    return !ignore;
                }
            }
            return ignore;
        }, features);
    }

    /**
     * 解析json字符串中某路径的值
     * @param json 代表一个对象的Json字符串
     * @param path 逗号分隔的json层次结构
     * @param <E> 属性类型
     * @return 从json字符串解析出来的路径值
     */
    @SuppressWarnings("unchecked")
    public static <E> E parse(final String json, final String path) {
        String[] keys = path.split(",");
        JSONObject obj = JSON.parseObject(json);
        for (int i = 0; i < keys.length - 1; i++) {
            obj = JSON.parseObject(keys[i]);
        }
        return (E) obj.get(keys[keys.length - 1]);
    }

    /**
     * json字符串解析为对象
     * @param json 代表一个对象的Json字符串
     * @param clazz 指定目标对象的类型，即返回对象的类型
     * @param <T> 解析对象类型
     * @return 从json字符串解析出来的对象
     */
    public static <T> T parse(final String json, final Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * json字符串解析为对象
     * @param json 代表一个对象的Json字符串
     * @param path 逗号分隔的json层次结构
     * @param clazz 指定目标对象的类型，即返回对象的类型
     * @param <T> 解析对象类型
     * @return 从json字符串解析出来的对象
     */
    public static <T> T parse(final String json, final String path, final Class<T> clazz) {
        String[] keys = path.split(",");
        JSONObject obj = JSON.parseObject(json);
        for (int i = 0; i < keys.length - 1; i++) {
            obj = JSON.parseObject(keys[i]);
        }
        String inner = obj.getString(keys[keys.length - 1]);
        return parse(inner, clazz);
    }

    /**
     * 将制定的对象经过字段过滤处理后，解析成为json集合
     * @param obj 待转化对象
     * @param fields 过滤处理字段
     * @param ignore true做忽略处理，false做包含处理
     * @param clazz 指定目标对象的类型，即返回对象的类型
     * @param features json特征，为null忽略
     * @param <T> 解析对象类型
     * @return 从json字符串解析出来的列表对象
     */
    public static <T> List<T> parseArray(final Object obj, final String[] fields, boolean ignore, final Class<T> clazz,
                                         final SerializerFeature... features) {
        String json = toJson(obj, fields, ignore, features);
        return parseArray(json, clazz);
    }

    /**
     * 从json字符串中解析出一个对象的集合，被解析字符串要求是合法的集合类型
     * （形如:["k1":"v1","k2":"v2",..."kn":"vn"]）
     * @param json 代表一个对象的Json字符串
     * @param clazz 指定目标对象的类型，即返回对象的类型
     * @param <T> 解析对象类型
     * @return 从json字符串解析出来的列表对象
     */
    public static <T> List<T> parseArray(final String json, final Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * 从json字符串中按照路径寻找，并解析出一个对象的集合，例如：
     * 类Person有一个属性name，要从以下json中解析出其集合：
     * {
     *   "page_info":{
     *     "items":{
     *       "item":[{"name":"KelvinZ"},{"name":"Jobs"},...{"name":"Gates"}]
     *     }
     *   }
     * 使用方法：parseArray(json, "page_info,items,item", Person.class)，
     * 将根据指定路径，正确的解析出所需集合，排除外层干扰
     * @param json 代表一个对象的Json字符串
     * @param path 逗号分隔的json层次结构
     * @param clazz 指定目标对象的类型，即返回对象的类型
     * @param <T> 解析对象类型
     * @return 从json字符串解析出来的列表对象
     */
    public static <T> List<T> parseArray(final String json, final String path, final Class<T> clazz) {
        String[] keys = path.split(",");
        JSONObject obj = JSON.parseObject(json);
        for (int i = 0; i < keys.length - 1; i++) {
            obj = JSON.parseObject(keys[i]);
        }
        String inner = obj.getString(keys[keys.length - 1]);
        return parseArray(inner, clazz);
    }

    /**
     * 有些json的常见格式错误这里可以处理，以便给后续的方法处理
     * 常见错误：使用了\" 或者 "{ 或者 }"
     * @param invalidJson 包含非法格式的json字符串
     * @return String
     */
    public static String correctJson(final String invalidJson) {
        return invalidJson.replace("\\\"", "\"").replace("\"{", "{").
                replace("}\"", "}");
    }

    /**
     * 格式化Json
     * @param json 代表一个对象的Json字符串
     * @return String
     */
    public static String formatJson(String json) {
        Map<?, ?> map = (Map<?, ?>) JSON.parse(json);
        return JSON.toJSONString(map, true);
    }

    /**
     * 获取json串中的子json
     * @param json 代表一个对象的Json字符串
     * @param path 逗号分隔的json层次结构
     * @return String
     */
    public static String getSubJson(String json, String path) {
        String[] keys = path.split(",");
        JSONObject obj = JSON.parseObject(json);
        for (int i = 0; i < keys.length - 1; i++) {
            obj = JSON.parseObject(keys[i]);
        }
        return obj != null ? obj.getString(keys[keys.length - 1]) : null;
    }
}
