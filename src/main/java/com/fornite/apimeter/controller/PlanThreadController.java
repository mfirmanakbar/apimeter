package com.fornite.apimeter.controller;

import com.fornite.apimeter.entity.Plan;
import com.fornite.apimeter.entity.PlanThread;
import com.fornite.apimeter.service.PlanService;
import com.fornite.apimeter.service.PlanThreadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Controller
@RequestMapping("/threads")
public class PlanThreadController {

    @Autowired
    PlanService planService;

    @Autowired
    PlanThreadService planThreadService;

    @Async("asyncExecutor")
    @GetMapping("/{id}/start")
    public CompletableFuture<String> start(Model model, @PathVariable("id") long id) {
        Plan plan = planService.findById(id);
        if (plan == null) {
            return CompletableFuture.completedFuture("redirect:/plans");
        }
        //CompletableFuture.runAsync(() -> planThreadService.threadRun(plan));
        CompletableFuture.runAsync(() -> planThreadService.threadRunCf(plan));
        return CompletableFuture.completedFuture("redirect:/plans/" + id + "/running");
    }

    @GetMapping("/{id}/clear")
    public String clear(Model model, @PathVariable("id") long id) {
        Plan plan = planService.findById(id);
        if (plan == null)
            return "redirect:/plans";

        List<PlanThread> threads = planThreadService.findByPlanId(id);
        if (threads == null)
            return "redirect:/plans/" + id + "/running";

        planThreadService.deleteByPlanId(id);
        log.info("TimeToRedirect: {}", new Date());
        return "redirect:/plans/" + id + "/running";
    }

}
