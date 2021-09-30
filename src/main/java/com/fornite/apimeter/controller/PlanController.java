package com.fornite.apimeter.controller;

import com.fornite.apimeter.request.PlanReq;
import com.fornite.apimeter.service.PlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    PlanService planService;

    @GetMapping("")
    public String welcome(Model model) {
        model.addAttribute("title", "Test Plans");
        return "/plan/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        PlanReq planReq = new PlanReq();
        String placeholderReqHeader = "Content-Type:application/json\nAuthorization:123456\n";
        String placeholderReqBody = "param1:123\nparam2:abc\n";

        model.addAttribute("title", "Create a Test Plan");
        model.addAttribute("planReq", planReq);
        model.addAttribute("placeholderReqHeader", placeholderReqHeader);
        model.addAttribute("placeholderReqBody", placeholderReqBody);

        return "/plan/create";
    }

    @PostMapping("/create")
    public String save(Model model, PlanReq planReq) {
        planService.save(planReq);
        return "redirect:/plans";
    }
}
