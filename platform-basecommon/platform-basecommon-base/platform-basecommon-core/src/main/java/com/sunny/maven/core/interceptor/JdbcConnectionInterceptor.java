package com.sunny.maven.core.interceptor;

import com.sunny.maven.core.datasource.JdbcDatasource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author SUNNY
 * @description: datasource获取jdbc连接的拦截器
 * @create: 2022-01-13 23:16
 */
public class JdbcConnectionInterceptor implements InvocationHandler {

    private JdbcDatasource datasource;
    public JdbcConnectionInterceptor(JdbcDatasource datasource) {
        super();
        this.datasource = datasource;
    }

    /**
     * 拦截datasource，根据登陆信息动态设置数据源
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(datasource.getCurrentYearDs(), args);
        // 判断是否是获得数据库链接的方法，可以做扩展实现
        if (method.getName().equalsIgnoreCase("getConnection")) {

        }
        return result;
    }
}
