package com.fornite.apimeter.service;

import com.fornite.apimeter.entity.PlanThread;
import com.fornite.apimeter.repository.PlanThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
