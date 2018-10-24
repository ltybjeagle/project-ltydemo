package com.liuty.maven.httpclient;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Apache HttpClient Tools
 */
public class ApacheHttpClient {

    private static final Logger LOG = LoggerFactory.getLogger(ApacheHttpClient.class);
    // 超时时间
    private static final int TIMEOUT = 3000;
    // 客户端请求配置
    private static RequestConfig config = null;
    private static final String ERROR_CODE = "500";
    private static final String SUCCESS_CODE = "200";

    /**
     * HTTP GET 请求
     * @param url
     * @return
     */
    public static String executeHttpGet(String url) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("executeHttpGet start ...");
        }
        JSONObject result = new JSONObject();

        HttpClient httpclient = getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpclient.execute(httpGet);
        } catch (IOException ex) {
            if (LOG.isErrorEnabled()) {
                LOG.error("executeHttpGet execute sendRequest error: " + ex.getMessage());
            }
            result.put(ERROR_CODE, ex.getMessage());
            return result.toString();
        }
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (200 != statusCode) {
            if (LOG.isErrorEnabled()) {
                LOG.error("executeHttpGet HttpResponse error, statusCode: " + statusCode);
            }
            result.put(ERROR_CODE, "service error, statusCode: " + statusCode);
            return result.toString();
        }
        try {
            String response = EntityUtils.toString(httpResponse.getEntity(), Charset.forName("UTF-8"));
            if (LOG.isDebugEnabled()) {
                LOG.debug("response message: {} \r\nexecuteHttpPost end ...", response);
            }
            result.put(SUCCESS_CODE, response);
            return result.toString();
        } catch (IOException ex) {
            if (LOG.isErrorEnabled()) {
                LOG.error("executeHttpGet execute EntityUtils error: " + ex.getMessage());
            }
            result.put(ERROR_CODE, ex.getMessage());
            return result.toString();
        }
    }

    /**
     * HTTP POST 请求
     * @param url
     * @param obj
     * @return
     */
    public static String executeHttpPost(String url, Object obj) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("executeHttpPost start ...");
        }
        JSONObject result = new JSONObject();

        // 构建消息实体
        StringEntity entity = null;
        try {
            JSONObject json = JSONObject.fromObject(obj);
            entity = new StringEntity(json.toString());
            entity.setContentType("application/json");
            entity.setContentEncoding("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            if (LOG.isErrorEnabled()) {
                LOG.error("executeHttpPost execute StringEntity error: " + ex.getMessage());
            }
            result.put(ERROR_CODE, ex.getMessage());
            return result.toString();
        }

        HttpClient httpclient = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        httpPost.addHeader("encoding", "UTF-8");
        httpPost.addHeader("Accept", "application/json");
        httpPost.setEntity(entity);

        try {
            HttpResponse httpResponse = httpclient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                if (LOG.isErrorEnabled()) {
                    LOG.error("executeHttpPost HttpResponse error, statusCode: " + statusCode);
                }
                result.put(ERROR_CODE, "service error, statusCode: " + statusCode);
                return result.toString();
            }
            String response = EntityUtils.toString(httpResponse.getEntity(), Charset.forName("UTF-8"));
            if (LOG.isDebugEnabled()) {
                LOG.debug("response message: {} \r\nexecuteHttpPost end ...", response);
            }
            result.put(SUCCESS_CODE, response);
            return result.toString();
        } catch (IOException ex) {
            if (LOG.isErrorEnabled()) {
                LOG.error("executeHttpPost execute sendRequest error: " + ex.getMessage());
            }
            result.put(ERROR_CODE, ex.getMessage());
            return result.toString();
        }
    }

    /**
     * 获取客户端
     * @return
     */
    private static HttpClient getHttpClient() {
        if (config == null) {
            synchronized (ApacheHttpClient.class) {
                if (config == null) {
                    config = RequestConfig.custom().setConnectTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).build();
                }
            }
        }
        return HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }
}
