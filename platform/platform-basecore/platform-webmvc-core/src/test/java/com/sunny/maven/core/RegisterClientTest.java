package com.sunny.maven.core;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author SUNNY
 * @description: ConfigClient测试
 * @create: 2022-08-17 15:24
 */
@Slf4j
public class RegisterClientTest {
    private CloseableHttpClient httpClient;
    private RequestConfig requestConfig;
    @Before
    public void init() {
        this.httpClient = HttpClientBuilder.create().build();
        // httpClient 客户端超时时间要大于长轮询约定的超时时间
        this.requestConfig = RequestConfig.custom().setSocketTimeout(40000).build();
    }
    @Test
    public void configClientTest() {
        // 对 dataId: user 进行配置监听
        longPolling("http://127.0.0.1:9010/register/listener", "user");
    }
    @SneakyThrows
    public void longPolling(String url, String dataId) {
        String endPoint = url + "?serverName=" + dataId;
        HttpGet httpGet = new HttpGet(endPoint);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        log.info("响应：{}", response);
        switch (response.getStatusLine().getStatusCode()) {
            case 200: {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                response.close();
                String configInfo = result.toString();
                log.info("dataId: [{}] changed, receive configInfo: {}", dataId, configInfo);
                longPolling(url, dataId);
                break;
            }
            // 304 响应码标记配置未变更
            case 304: {
                log.info("longPolling dataId: [{}] once finished, configInfo is unchanged, longPolling again", dataId);
                longPolling(url, dataId);
                break;
            }
            default: {
                throw new RuntimeException("unExcepted HTTP status code");
            }
        }
    }
}
