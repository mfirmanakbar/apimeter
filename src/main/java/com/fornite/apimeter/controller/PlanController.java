package com.fornite.apimeter.controller;

import com.fornite.apimeter.entity.Plan;
import com.fornite.apimeter.request.PlanReq;
import com.fornite.apimeter.service.PlanService;
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

    @GetMapping("")
    public String welcome(Model model) {
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
        String placeholderReqHeader = "Content-Type:application/json\nAuthorization:123456\n";
        String placeholderReqBody = "param1:123\nparam2:abc\n";

        Plan plan = planService.findById(id);
        if (plan == null) {
            return "redirect:/plans";
        }

        model.addAttribute("title", "Update Plan");
        model.addAttribute("plan", plan);
        model.addAttribute("placeholderReqHeader", placeholderReqHeader);
        model.addAttribute("placeholderReqBody", placeholderReqBody);

        return "/plan/create-or-update";
    }

    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable("id") long id) {
        planService.delete(id);
        return "redirect:/plans";
    }

    @PostMapping("/createOrUpdate")
    public String save(Model model, Plan plan) {

        if (plan.getId() == 0) {
            plan.setCreatedAt(new Date());
            plan.setUpdatedAt(new Date());
        } else {
            Plan existingPlan = planService.findById(plan.getId());
            plan.setCreatedAt(existingPlan.getCreatedAt());
            plan.setUpdatedAt(new Date());
        }

        planService.save(plan);
        return "redirect:/plans";
    }
}
