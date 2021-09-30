package com.fornite.apimeter.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PlanReq {
    private long id;
    private String method;
    private String planName;
    private long numberOfThreads;
    private String url;
    private String reqHeader;
    private String reqBody;
}
