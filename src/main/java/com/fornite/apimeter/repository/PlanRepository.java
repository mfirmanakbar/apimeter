package com.fornite.apimeter.repository;

import com.fornite.apimeter.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Query(value = "SELECT * FROM plans order BY id DESC", nativeQuery = true)
    List<Plan> findAllDesc();

}
