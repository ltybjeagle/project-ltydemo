package com.sunny.maven.core.dal.mybatis.interceptor;

import com.sunny.maven.core.annotation.database.encryption.SensitiveData;
import com.sunny.maven.core.dal.encryption.api.IDecrypt;
import com.sunny.maven.core.dal.encryption.api.impl.DecryptImpl;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;

/**
 * @author SUNNY
 * @description: 返回值插件 ResultSetInterceptor
 * 切入 Mybatis 返回结果对敏感数据进行解密
 * @create: 2023/5/26 11:27
 */
@Component
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}),
})
public class ResultSetInterceptor implements Interceptor {
    private IDecrypt iDecrypt;
    @Override
    @SuppressWarnings("unchecked")
    public Object intercept(Invocation invocation) throws Throwable {
        // 取出查询的结果
        Object resultObject = invocation.proceed();
        if (Objects.isNull(resultObject)) {
            return null;
        }
        // 基于selectList
        if (resultObject instanceof ArrayList) {
            ArrayList<Objects> resultList = (ArrayList<Objects>) resultObject;
            if (!CollectionUtils.isEmpty(resultList) && needToDecrypt(resultList.get(0))) {
                for (Object result : resultList) {
                    iDecrypt.decrypt(result);
                }
            }
        } else {
            // 基于selectOne
            if (needToDecrypt(resultObject)) {
                iDecrypt.decrypt(resultObject);
            }
        }

        return resultObject;
    }

    private boolean needToDecrypt(Object object) {
        Class<?> objectClass = object.getClass();
        SensitiveData sensitiveData = AnnotationUtils.findAnnotation(objectClass, SensitiveData.class);
        return Objects.nonNull(sensitiveData);
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }

    public ResultSetInterceptor() {
        iDecrypt = DecryptImpl.getInstance();
    }
}
