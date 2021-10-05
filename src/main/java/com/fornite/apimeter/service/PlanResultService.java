package com.fornite.apimeter.service;

import com.fornite.apimeter.entity.PlanResult;

import java.util.List;

public interface PlanResultService {
    List<PlanResult> findByThreadId(long threadId);

    void deleteByPlanId(long planId);
}
