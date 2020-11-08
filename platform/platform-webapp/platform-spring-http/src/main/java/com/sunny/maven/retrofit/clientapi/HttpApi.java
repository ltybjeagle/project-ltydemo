package com.sunny.maven.retrofit.clientapi;

import com.github.lianjiatech.retrofit.spring.boot.annotation.Intercept;
import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import com.sunny.maven.retrofit.annotation.Sign;
import com.sunny.maven.retrofit.clientapi.interceptor.TimeStampInterceptor;
import com.sunny.maven.retrofit.dto.Person;
import com.sunny.maven.retrofit.vo.Result;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.concurrent.CompletableFuture;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/8/22 0:18
 * @description：请求接口
 * @modified By：
 * @version: 1.0.0
 */
@RetrofitClient(baseUrl = "${httpApi.baseUrl}", poolName="test1")
@Intercept(handler = TimeStampInterceptor.class, include = {"/api/**"}, exclude = "/api/test/savePerson")
@Sign(accessKeyId = "${httpApi.accessKeyId}", accessKeySecret = "${httpApi.accessKeySecret}", exclude = {"/api/test/person"})
public interface HttpApi {

    @POST("savePerson")
    Result<Person> savePerson(@Body Person person);

    /**
     * Call<T>
     * 不执行适配处理，直接返回Call<T>对象
     * @param id
     * @return
     */
    @GET("person")
    Call<Result<Person>> getPersonCall(@Query("id") Long id);

    /**
     *  CompletableFuture<T>
     *  将响应体内容适配成CompletableFuture<T>对象返回
     * @param id
     * @return
     */
    @GET("person")
    CompletableFuture<Result<Person>> getPersonCompletableFuture(@Query("id") Long id);

    /**
     * Void
     * 不关注返回类型可以使用Void。如果http状态码不是2xx，直接抛错！
     * @param id
     * @return
     */
    @GET("person")
    Void getPersonVoid(@Query("id") Long id);

    /**
     *  Response<T>
     *  将响应内容适配成Response<T>对象返回
     * @param id
     * @return
     */
    @GET("person")
    Response<Result<Person>> getPersonResponse(@Query("id") Long id);

    /**
     * 其他任意Java类型
     * 将响应体内容适配成一个对应的Java类型对象返回，如果http状态码不是2xx，直接抛错！
     * @param id
     * @return
     */
    @GET("person")
    Result<Person> getPerson(@Query("id") Long id);
}
