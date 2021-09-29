package com.fornite.apimeter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/plan")
public class PlanController {

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("title", "Plans");
        return "/plan/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("title", "Create new Plan");
        return "/plan/create";
    }
}
