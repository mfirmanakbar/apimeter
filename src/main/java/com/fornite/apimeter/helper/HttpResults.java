package com.fornite.apimeter.helper;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HttpResults {
    private String title;
    private String url;
    private int code;
    private String header;
    private double time;
    private String bodyRequest;
    private String bodyResponse;
}
