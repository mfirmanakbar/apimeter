package com.fornite.apimeter.helper;

import org.apache.commons.lang3.StringUtils;
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

public class HttpHelper {

    protected static Logger log = LoggerFactory.getLogger(HttpHelper.class);

    public static HttpResults requestByPost(HttpParam httpParam, HeaderSet<HttpPost, HttpPost> headerSet) {
        HttpClient httpClient = null;
        HttpResponse response = null;
        String title = httpParam.getTitle();
        String url = httpParam.getUrl();
        HttpPost httpPost = new HttpPost(url);
        String contentTypeDefaultValue = "application/json;charset=" + httpParam.getCharset();
        String contentType = StringUtils.defaultString(httpParam.getContentType(), contentTypeDefaultValue);

        int resHttpCode = 0;
        String resHeader = "";
        String resParam = "";
        String resResult = "";
        double usedTime = -1;

        try {
            httpPost.setHeader("Content-Type", contentType);
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
        return HttpResults.builder()
                .title(title)
                .url(url)
                .code(resHttpCode)
                .header(resHeader)
                .param(resParam)
                .response(resResult)
                .time(usedTime)
                .build();
    }

    /*public static HttpResults requestByGet(HttpParam requestParam, HeaderSet<HttpGet, HttpGet> headerSet) {
        return HttpResults.builder()
                .title(title)
                .url(url)
                .code(resHttpCode)
                .header(resHeader)
                .param(resParam)
                .response(resResult)
                .time(usedTime)
                .build();
    }*/

    private static void loggers(String title, String url, HttpResponse response, HttpPost httpPost,
                                HttpParam httpParam, double usedTime, String resultContent) {
        /*log.info("{}Url: {}", title, url);
        log.info("{}Code: {}", title, response.getStatusLine().getStatusCode());
        log.info("{}Header: {}", title, Arrays.asList(httpPost.getAllHeaders()));
        log.info("{}Param: {}", title, httpParam.getData());
        log.info("{}Result: {}", title, cleanJsonWhiteSpace(resultContent));
        log.info("{}Time: {}", title, usedTime);*/

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
