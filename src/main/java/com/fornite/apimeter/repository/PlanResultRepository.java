package com.fornite.apimeter.repository;

import com.fornite.apimeter.entity.PlanResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PlanResultRepository extends JpaRepository<PlanResult, Long> {

    List<PlanResult> findByThreadId(long threadId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM plans_results p WHERE p.plan_id = :planId", nativeQuery = true)
    void deleteWithPlanId(long planId);
}
