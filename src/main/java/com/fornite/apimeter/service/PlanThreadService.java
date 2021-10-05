package com.fornite.apimeter.service;

import com.fornite.apimeter.entity.Plan;
import com.fornite.apimeter.entity.PlanThread;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PlanThreadService {
    List<PlanThread> findAllDesc();

    List<PlanThread> findAll();

    void deleteByPlanId(long planId);

    PlanThread findById(long id);

    List<PlanThread> findByPlanId(long planId);

    void threadRun(Plan plan);

    CompletableFuture<?> threadRunCf(Plan plan);
}
