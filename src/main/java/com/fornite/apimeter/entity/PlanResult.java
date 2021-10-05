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
@Table(name = "plans_results")
public class PlanResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long planId;
    private long threadId;
    private Date createdAt;

    @Lob
    private String responseHeader;

    @Lob
    private String responseBody;
}
