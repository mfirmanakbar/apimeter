package com.fornite.apimeter.repository;

import com.fornite.apimeter.entity.PlanResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanResultRepository extends JpaRepository<PlanResult, Long> {

}
