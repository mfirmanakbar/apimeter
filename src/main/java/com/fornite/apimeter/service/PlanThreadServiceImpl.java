package com.fornite.apimeter.service;

import com.fornite.apimeter.entity.Plan;
import com.fornite.apimeter.entity.PlanThread;
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
    public long deleteByPlanId(long planId) {
        return planThreadRepository.deleteByPlanId(planId);
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
    public void threadRun(Plan plan) {
        /*for (int i = 0; i < plan.getNumberOfThreads(); i++) {
            PlanThreadRun threadRun = new PlanThreadRun(plan);
            threadRun.start();
        }*/
        httpRequestThread(plan);
    }

    @Override
    public CompletableFuture<?> threadRunCf(Plan plan) {
        for (int i = 0; i < plan.getNumberOfThreads(); i++) {
            CompletableFuture.runAsync(() -> {
                log.info("#Name: {} - #Time: {}", Thread.currentThread().getName(), new Date());
                httpRequestThreadCF(plan);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        return CompletableFuture.completedFuture(null);
    }

    private void httpRequestThreadCF(Plan plan) {
        /*CompletableFuture.supplyAsync(() -> {

        });*/
    }

    private void httpRequestThread(Plan plan) {
        Runnable runnable = () -> {
            String threadName = Thread.currentThread().getName();
            log.info(
                    "ThreadName: {} - ThreadTime: {}",
                    threadName, new Date()
            );
        };

        for (int i = 0; i < plan.getNumberOfThreads(); i++) {
            Thread thr = new Thread(runnable, "The Thread " + i);
            thr.start();
        }
    }
}
