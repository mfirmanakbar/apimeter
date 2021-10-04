package com.fornite.apimeter.service;

import com.fornite.apimeter.entity.Plan;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class PlanThreadRun extends Thread {

    Plan plan;

    public PlanThreadRun(Plan plan) {
        this.plan = plan;
    }

    @Override
    public void run() {
        httpRequest(plan);
    }

    private void httpRequest(Plan plan) {
        log.info(
                "PlanName: {} - ThreadName: {} - Time: {}",
                plan.getPlanName(), Thread.currentThread().getName(), new Date()
        );
    }
}
