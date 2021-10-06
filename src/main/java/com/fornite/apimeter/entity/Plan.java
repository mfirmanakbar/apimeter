package com.fornite.apimeter.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "plans")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date createdAt;
    private Date updatedAt;
    private String method;
    private String planName;
    private long numberOfThreads;
    private long period;
    private long looping;
    private String url;

    @Lob
    private String requestHeader;

    @Lob
    private String requestBody;
}
