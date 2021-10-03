package com.fornite.apimeter.service;

import com.fornite.apimeter.entity.Plan;

import java.util.List;

public interface PlanService {

    Plan save(Plan plan);

    Plan findById(long id);

    void delete(long id);

    List<Plan> findAllDesc();
}
