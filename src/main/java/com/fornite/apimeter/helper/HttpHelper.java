package com.fornite.apimeter.helper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class HttpHelper {

    protected static Logger log = LoggerFactory.getLogger(HttpHelper.class);
    protected static int resHttpCode = 0;
    protected static String resHeader = "", resParam = "", resResult = "";
    protected static double usedTime = -1;
    protected static HttpClient httpClient = null;
    protected static HttpResponse response = null;

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
            String resultContent = EntityUtils.toString(entity, httpParam.getCharset());
            resHttpCode = response.getStatusLine().getStatusCode();
            resHeader = Arrays.asList(httpPost.getAllHeaders()).toString();
            resParam = httpParam.getData();
            loggers(title, url, response, httpPost, httpParam, usedTime, resultContent);
        } catch (Exception e) {
            loggers(title, url, null, httpPost, httpParam, 0, null);
        } finally {
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }

        return new HttpResults(title, url, resHttpCode, resHeader, resParam, resResult, usedTime);
    }

    private static void loggers(String title, String url, HttpResponse response, HttpPost httpPost,
                                HttpParam httpParam, double usedTime, String resultContent) {
        log.info("{}Url: {}, Status: {}, Headers: {}, Request: {}, Response: {}, Duration: {}",
                title, url, response.getStatusLine().getStatusCode(), Arrays.asList(httpPost.getAllHeaders()),
                httpParam.getData(), cleanJsonWhiteSpace(resultContent), usedTime);
    }

    private static String cleanJsonWhiteSpace(String resultContent) {
        return resultContent.replaceAll("[\\r\\n]+", "").trim()
                .replace("\": \"", "\":\"")
                .replace("\",  \"", "\",\"")
                .replace("{  \"", "{\"")
                .replace("\"  }", "\"}");
    }
}
