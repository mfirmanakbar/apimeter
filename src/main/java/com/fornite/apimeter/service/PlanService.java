package com.fornite.apimeter.service;

import com.fornite.apimeter.entity.Plan;
import com.fornite.apimeter.request.PlanReq;

public interface PlanService {
    Plan save(PlanReq planReq);
}
