package com.fornite.apimeter.service;

import com.fornite.apimeter.entity.PlanThread;

import java.util.List;

public interface PlanThreadService {
    List<PlanThread> findAllDesc();

    List<PlanThread> findAll();
}
