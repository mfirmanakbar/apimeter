package com.fornite.apimeter.service;

import com.fornite.apimeter.entity.Plan;
import com.fornite.apimeter.repository.PlanRepository;
import com.fornite.apimeter.request.PlanReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    PlanRepository planRepository;

    @Override
    public Plan save(PlanReq planReq) {
        return planRepository.save(
                Plan.builder()
                        .createdAt(new Date())
                        .updatedAt(new Date())
                        .method(planReq.getMethod())
                        .planName(planReq.getPlanName())
                        .numberOfThreads(planReq.getNumberOfThreads())
                        .url(planReq.getUrl())
                        .reqHeader(planReq.getReqHeader())
                        .reqBody(planReq.getReqBody())
                        .build()
        );
    }
}
