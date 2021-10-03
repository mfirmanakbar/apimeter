package com.fornite.apimeter.controller;

import com.fornite.apimeter.entity.Plan;
import com.fornite.apimeter.entity.PlanThread;
import com.fornite.apimeter.service.PlanService;
import com.fornite.apimeter.service.PlanThreadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    PlanService planService;

    @Autowired
    PlanThreadService planThreadService;

    @GetMapping("")
    public String index(Model model) {
        List<Plan> plans = planService.findAllDesc();
        model.addAttribute("title", "Test Plans");
        model.addAttribute("plans", plans);
        return "/plan/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Plan plan = new Plan();
        String placeholderReqHeader = "Content-Type:application/json\nAuthorization:123456\n";
        String placeholderReqBody = "param1:123\nparam2:abc\n";

        model.addAttribute("title", "Create Plan");
        model.addAttribute("plan", plan);
        model.addAttribute("placeholderReqHeader", placeholderReqHeader);
        model.addAttribute("placeholderReqBody", placeholderReqBody);

        return "/plan/create-or-update";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        return viewPageAndEditPage(model, id, "Edit");
    }

    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable("id") long id) {
        planService.delete(id);
        return "redirect:/plans";
    }

    @GetMapping("/{id}/view")
    public String view(Model model, @PathVariable("id") long id) {
        return viewPageAndEditPage(model, id, "View");
    }

    @GetMapping("/{id}/running")
    public String running(Model model, @PathVariable("id") long id) {
        Plan plan = planService.findById(id);
        if (plan == null) {
            return "redirect:/plans";
        }

        List<PlanThread> threads = planThreadService.findAllDesc();

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

    private String viewPageAndEditPage(Model model, long id, String fun) {
        String placeholderReqHeader = "Content-Type:application/json\nAuthorization:123456\n";
        String placeholderReqBody = "param1:123\nparam2:abc\n";
        boolean isSubmit = false;

        Plan plan = planService.findById(id);
        if (plan == null) {
            return "redirect:/plans";
        }

        if (fun.equalsIgnoreCase("Edit")) {
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
