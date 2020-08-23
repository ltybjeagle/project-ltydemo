package com.sunny.maven.retrofit.clientapi;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import com.sunny.maven.retrofit.dto.Person;
import com.sunny.maven.retrofit.vo.Result;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/8/22 0:18
 * @description：请求接口
 * @modified By：
 * @version: 1.0.0
 */
@RetrofitClient(baseUrl = "${test.baseUrl}")
public interface HttpApi {
    @GET("person")
    Result<Person> getPerson(@Query("id") Long id);
}
