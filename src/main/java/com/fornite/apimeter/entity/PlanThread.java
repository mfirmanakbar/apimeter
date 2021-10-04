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
@Table(name = "plans_threads")
public class PlanThread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long planId;
    private Date createdAt;
    private String threadName;
    private double executionTime;
    private int codeStatus;
}
