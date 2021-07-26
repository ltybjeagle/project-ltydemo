package com.sunny.maven.httpclient;

import com.sunny.maven.httpclient.client.HttpClientUtil;
import org.junit.Test;

/**
 * @author SUNNY
 * @description: httpclient测试用例
 * @create: 2021-07-26 19:14
 */
public class HttpClientTest {
    private static final String URL = "http://122.112.250.17:30080/fasp/restapi/v1/sec/tokenids/%s/userinfo";

    /**
     * HttpClient GET 请求测试用例
     */
    @Test
    public void testHttpGet() {
        String tokenId = "ACAD1630EB52640EF79AF86D895FFF9EE5apMicG";
        String httpUrl = String.format(URL, tokenId);
        System.out.println(httpUrl);
        String result = HttpClientUtil.doGet(httpUrl);
        System.out.println(result);
    }
}
