package com.fornite.apimeter.controller;

import com.fornite.apimeter.entity.Plan;
import com.fornite.apimeter.entity.PlanResult;
import com.fornite.apimeter.entity.PlanThread;
import com.fornite.apimeter.service.PlanResultService;
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
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    PlanService planService;

    @Autowired
    PlanThreadService planThreadService;

    @Autowired
    PlanResultService planResultService;

    @GetMapping("")
    public String index(Model model) {
        List<Plan> plans = planService.findAllDesc();
        model.addAttribute("title", "Test Plans");
        model.addAttribute("plans", plans);
        return "/plan/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        return viewCreateEditPage(model, 0, "Create", new Plan());
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        Plan plan = planService.findById(id);
        if (plan == null) {
            return "redirect:/plans";
        }
        return viewCreateEditPage(model, id, "Edit", plan);
    }

    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable("id") long id) {
        planService.deletePlanThreadResult(id);
        return "redirect:/plans";
    }

    @GetMapping("/{id}/view")
    public String view(Model model, @PathVariable("id") long id) {
        Plan plan = planService.findById(id);
        if (plan == null) {
            return "redirect:/plans";
        }
        return viewCreateEditPage(model, id, "View", plan);
    }

    @GetMapping("/{id}/threads")
    public String running(Model model, @PathVariable("id") long id) {
        Plan plan = planService.findById(id);
        if (plan == null) {
            return "redirect:/plans";
        }

        List<PlanThread> threads = planThreadService.findByPlanId(id);

        model.addAttribute("title", "Run a Plan ID: " + id);
        model.addAttribute("plan", plan);
        model.addAttribute("threads", threads);
        return "/plan/thread/index";
    }

    @PostMapping("/createOrUpdate")
    public String save(Model model, Plan plan) {
        plan.setCreatedAt(new Date());

        if (plan.getId() == 0) {
            plan.setUpdatedAt(new Date());
        } else {
            Plan existingPlan = planService.findById(plan.getId());
            plan.setCreatedAt(existingPlan.getCreatedAt());
        }

        planService.save(plan);
        return "redirect:/plans";
    }

    @Async("asyncExecutor")
    @GetMapping("/{id}/threads/start")
    public CompletableFuture<String> start(Model model, @PathVariable("id") long id) {
        Plan plan = planService.findById(id);
        if (plan == null) {
            return CompletableFuture.completedFuture("redirect:/plans");
        }
        CompletableFuture.runAsync(() -> planThreadService.threadRunCf(plan));
        return CompletableFuture.completedFuture("redirect:/plans/" + id + "/threads");
    }

    @GetMapping("/{id}/threads/clear")
    public String deleteThreadResult(Model model, @PathVariable("id") long id) {
        planService.deleteThreadResult(id);
        return "redirect:/plans/" + id + "/threads";
    }

    @GetMapping("/{planId}/threads/{threadId}")
    public String clear(Model model, @PathVariable("planId") long planId, @PathVariable("threadId") long threadId) {
        List<PlanResult> planResult = planResultService.findByThreadId(threadId);

        model.addAttribute("title", "Thread Response : " + threadId);
        model.addAttribute("pResults", planResult);
        model.addAttribute("planId", planId);

        return "/plan/thread/result/index";
    }

    private String viewCreateEditPage(Model model, long id, String fun, Plan plan) {
        String placeholderReqHeader = "Content-Type:application/json\nAuthorization:123456\n";
        String placeholderReqBody = "param1:123\nparam2:abc\n";
        boolean isSubmit = false;

        if (fun.equalsIgnoreCase("Edit") || fun.equalsIgnoreCase("Create")) {
            isSubmit = true;
        }

        model.addAttribute("title", fun + " Plan");
        model.addAttribute("plan", plan);
        model.addAttribute("placeholderReqHeader", placeholderReqHeader);
        model.addAttribute("placeholderReqBody", placeholderReqBody);
        model.addAttribute("isSubmit", isSubmit);

        return "/plan/create-or-update";
    }

}
