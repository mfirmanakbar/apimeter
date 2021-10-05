package com.fornite.apimeter.service;

import com.fornite.apimeter.entity.PlanResult;
import com.fornite.apimeter.repository.PlanResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanResultServiceImpl implements PlanResultService {

    @Autowired
    PlanResultRepository planResultRepository;

    @Override
    public List<PlanResult> findByThreadId(long threadId) {
        return planResultRepository.findByThreadId(threadId);
    }

    @Override
    public void deleteByPlanId(long planId) {
        planResultRepository.deleteWithPlanId(planId);
    }
}
