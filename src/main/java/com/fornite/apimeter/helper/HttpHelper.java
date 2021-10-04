package com.fornite.apimeter.helper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class HttpHelper {

    protected static Logger log = LoggerFactory.getLogger(HttpHelper.class);
    protected static int resHttpCode = 0;
    protected static String resHeader = "", resParam = "", resResult = "";
    protected static double usedTime = -1;
    protected static HttpClient httpClient = null;
    protected static HttpResponse response = null;

    public static HttpResults requestByGet(HttpParam httpParam, HeaderSet<HttpGet, HttpGet> headerSet) {
        HttpClient httpClient = null;
        HttpResponse response = null;
        String title = httpParam.getTitle();
        String url = httpParam.getUrl();
        HttpGet httpGet = new HttpGet(url);
        try {
            httpGet = headerSet.setHeader(httpGet);
            if (null != httpParam.getRequestConfig()) {
                httpGet.setConfig(httpParam.getRequestConfig());
            }
            httpClient = HttpClients.createAuthNonHttpClient();
            long reqBeforeTime = System.currentTimeMillis();
            response = httpClient.execute(httpGet);
            usedTime = (System.currentTimeMillis() - reqBeforeTime) / 1000.00D;
            HttpEntity entity = response.getEntity();
            resResult = EntityUtils.toString(entity, httpParam.getCharset());
            resHttpCode = response.getStatusLine().getStatusCode();
            resHeader = Arrays.asList(httpGet.getAllHeaders()).toString();
            resParam = httpParam.getData();
            loggers(title, url, response, Arrays.asList(httpGet.getAllHeaders()), httpParam, usedTime, resResult);
        } catch (Exception e) {
            loggers(title, url, null, Arrays.asList(httpGet.getAllHeaders()), httpParam, 0, null);
        } finally {
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }
        return new HttpResults(title, url, resHttpCode, resHeader, usedTime, resParam, resResult);
    }

    public static HttpResults requestByPost(HttpParam httpParam, HeaderSet<HttpPost, HttpPost> headerSet) {
        String title = httpParam.getTitle();
        String url = httpParam.getUrl();
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setHeader("Content-Type", "application/json");
            httpPost = headerSet.setHeader(httpPost);
            HttpEntity postEntity = new StringEntity(httpParam.getData(), httpParam.getCharset());
            httpPost.setEntity(postEntity);
            if (httpParam.getRequestConfig() != null) {
                httpPost.setConfig(httpParam.getRequestConfig());
            }
            httpClient = HttpClients.createAuthNonHttpClient();
            long reqBeforeTime = System.currentTimeMillis();
            response = httpClient.execute(httpPost);
            usedTime = (System.currentTimeMillis() - reqBeforeTime) / 1000.00D;
            HttpEntity entity = response.getEntity();
            resResult = EntityUtils.toString(entity, httpParam.getCharset());
            resHttpCode = response.getStatusLine().getStatusCode();
            resHeader = Arrays.asList(httpPost.getAllHeaders()).toString();
            resParam = httpParam.getData();
            loggers(title, url, response, Arrays.asList(httpPost.getAllHeaders()), httpParam, usedTime, resResult);
        } catch (Exception e) {
            loggers(title, url, null, Arrays.asList(httpPost.getAllHeaders()), httpParam, 0, null);
        } finally {
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }
        return new HttpResults(title, url, resHttpCode, resHeader, usedTime, resParam, resResult);
    }

    private static void loggers(String title, String url, HttpResponse response, List<?> headers,
                                HttpParam httpParam, double usedTime, String resultContent) {
        int code = 520;
        if (response != null) {
            code = response.getStatusLine().getStatusCode();
        }

        log.info("Title: {}\nUrl: {}\nCode: {}\nHeaders: {}\nTime: {}\nRequest: {}\nResponse: {}",
                title, url, code, headers, usedTime, httpParam.getData(), resultContent);
    }

}
