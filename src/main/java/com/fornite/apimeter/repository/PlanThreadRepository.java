package com.fornite.apimeter.repository;

import com.fornite.apimeter.entity.PlanThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanThreadRepository extends JpaRepository<PlanThread, Long> {

    @Query(value = "SELECT * FROM plans_threads order BY id DESC", nativeQuery = true)
    List<PlanThread> findAllDesc();

    @Query(value = "DELETE FROM plans_threads WHERE plan_id = :planId", nativeQuery = true)
    long deleteByPlanId(long planId);

    List<PlanThread> findByPlanId(long planId);
}
