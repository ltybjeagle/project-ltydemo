package com.sunny.maven.core.httpclient.client;

import com.sunny.maven.core.httpclient.constant.HttpConstant;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author SUNNY
 * @description: http client工具类
 * @create: 2021-07-25 12:09
 */
public class HttpClientUtil {

    /**
     * 带参数的get请求
     * @param url 请求路径
     * @param param 请求参数
     * @return String
     */
    public static String doGet(String url, Map<String, String> param) {
        String resultString = "";
        CloseableHttpResponse response = null;

        // 创建Httpclient对象
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                param.forEach(builder::addParameter);
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            // 执行请求
            response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                resultString = EntityUtils.toString(response.getEntity(), HttpConstant.UTF8_ENCODE);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 不带参数的get请求
     * @param url 请求路径
     * @return String
     */
    public static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     * 带参数的post请求
     * @param url 请求路径
     * @param param 请求参数
     * @return String
     */
    public static String doPost(String url, Map<String, String> param) {
        String resultString = "";
        CloseableHttpResponse response = null;

        // 创建Httpclient对象
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                param.forEach((key, value) -> paramList.add(new BasicNameValuePair(key, value)));
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                resultString = EntityUtils.toString(response.getEntity(), HttpConstant.UTF8_ENCODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 不带参数的post请求
     * @param url 请求路径
     * @return String
     */
    public static String doPost(String url) {
        return doPost(url, null);
    }

    /**
     * 传送json类型的post请求
     * @param url 请求路径
     * @param json 请求JSON串
     * @return String
     */
    public static String doPostJson(String url, String json) {
        String resultString = "";
        CloseableHttpResponse response = null;

        // 创建Httpclient对象
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);

            // 创建请求内容
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);

            // 执行http请求
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                resultString = EntityUtils.toString(response.getEntity(), HttpConstant.UTF8_ENCODE);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return resultString;
    }
}
