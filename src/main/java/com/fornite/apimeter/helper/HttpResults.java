package com.fornite.apimeter.helper;

import lombok.Builder;

@Builder
public class HttpResults {
    private String title;
    private String url;
    private int code;
    private String header;
    private String param;
    private String response;
    private double time;
}
