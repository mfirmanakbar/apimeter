package com.fornite.apimeter.service;

import com.fornite.apimeter.entity.Plan;
import com.fornite.apimeter.entity.PlanResult;
import com.fornite.apimeter.entity.PlanThread;
import com.fornite.apimeter.helper.HttpHelper;
import com.fornite.apimeter.helper.HttpParam;
import com.fornite.apimeter.helper.HttpResults;
import com.fornite.apimeter.repository.PlanResultRepository;
import com.fornite.apimeter.repository.PlanThreadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class PlanThreadServiceImpl implements PlanThreadService {

    @Autowired
    PlanResultRepository planResultRepository;

    @Autowired
    PlanThreadRepository planThreadRepository;

    @Override
    public List<PlanThread> findAllDesc() {
        return planThreadRepository.findAllDesc();
    }

    @Override
    public List<PlanThread> findAll() {
        return planThreadRepository.findAll();
    }

    @Override
    public void deleteByPlanId(long planId) {
        planThreadRepository.deleteWithPlanId(planId);
    }

    @Override
    public PlanThread findById(long id) {
        return planThreadRepository.findById(id).orElse(null);
    }

    @Override
    public List<PlanThread> findByPlanId(long planId) {
        return planThreadRepository.findByPlanId(planId);
    }

    @Override
    public CompletableFuture<?> threadRunCf(Plan plan) {
        for (int a = 0; a < plan.getLooping(); a++) {
            for (int i = 0; i < plan.getNumberOfThreads(); i++) {
                CompletableFuture.runAsync(() -> {
                    //log.info("#Name: {} - #Time: {}", Thread.currentThread().getName(), new Date());
                    httpRequestThreadCF(plan);
                    if (plan.getPeriod() > 0) {
                        long period = plan.getPeriod() / plan.getNumberOfThreads();
                        try {
                            Thread.sleep(period);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
        return CompletableFuture.completedFuture(null);
    }

    private void httpRequestThreadCF(Plan plan) {
        CompletableFuture.runAsync(() -> {
            if (plan.getMethod().equals("GET"))
                httpGet(plan);
        });
    }

    private void httpGet(Plan plan) {
        HttpParam httpParam = new HttpParam(plan.getPlanName(), plan.getUrl(), plan.getRequestBody());
        HttpResults result = HttpHelper.requestByGet(httpParam, headerSet -> {
            //headerSet.setHeader("Auth", "123456");
            return headerSet;
        });

        storeThreadDb(plan, result);
    }

    private void storeThreadDb(Plan plan, HttpResults httpResults) {
        CompletableFuture.runAsync(() -> {
            PlanThread planThread = PlanThread.builder()
                    .planId(plan.getId())
                    .createdAt(new Date())
                    .threadName(Thread.currentThread().getName())
                    .executionTime(httpResults.getTime())
                    .codeStatus(httpResults.getCode())
                    .build();

            PlanThread planThreadSaved = planThreadRepository.save(planThread);

            if (planThreadSaved != null) {
                PlanResult planResult = PlanResult.builder()
                        .planId(plan.getId())
                        .threadId(planThreadSaved.getId())
                        .createdAt(new Date())
                        .responseHeader(null)
                        .responseBody(httpResults.getBodyResponse())
                        .build();
                planResultRepository.save(planResult);
            }

        });
    }

}
