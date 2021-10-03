package com.fornite.apimeter.service;

import com.fornite.apimeter.entity.Plan;
import com.fornite.apimeter.repository.PlanRepository;
import com.fornite.apimeter.request.PlanReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Override
    public Plan save(Plan plan) {
        return planRepository.save(plan);
    }

    @Override
    public Plan findById(long id) {
        return planRepository.findById(id).get();
    }

    @Override
    public void delete(long id) {
        planRepository.deleteById(id);
    }

    @Override
    public List<Plan> findAllDesc() {
        return planRepository.findAllDesc();
    }
}
