package com.sunny.maven.retrofit.clientapi.interceptor;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BaseGlobalInterceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/8/23 18:38
 * @description：全局拦截器
 * @modified By：
 * @version: 1.0.0
 */
@Component
public class SourceInterceptor extends BaseGlobalInterceptor {
    @Override
    protected Response doIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newReq = request.newBuilder()
                .addHeader("source", "test")
                .build();
        return chain.proceed(newReq);
    }
}
