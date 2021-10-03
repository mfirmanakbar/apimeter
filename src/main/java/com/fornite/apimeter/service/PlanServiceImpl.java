package com.fornite.apimeter.service;

import com.fornite.apimeter.entity.Plan;
import com.fornite.apimeter.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    PlanRepository planRepository;

    @Override
    public Plan save(Plan plan) {
        return planRepository.save(plan);
    }

    @Override
    public Plan findById(long id) {
        return planRepository.findById(id).orElse(null);
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
