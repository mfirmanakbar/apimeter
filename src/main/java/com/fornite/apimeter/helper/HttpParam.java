package com.fornite.apimeter.helper;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.client.config.RequestConfig;

@Setter
@Getter
public class HttpParam {
    private String title;
    private String url;
    private String data;
    private String contentType;
    private String charset = "UTF-8";
    private String method = "POST";
    private RequestConfig requestConfig;

    public HttpParam(String title, String url, String data) {
        this.title = title;
        this.url = url;
        this.data = data;
    }

}
