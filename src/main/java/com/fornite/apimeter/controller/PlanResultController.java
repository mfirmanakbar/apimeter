package com.fornite.apimeter.controller;

import com.fornite.apimeter.entity.PlanResult;
import com.fornite.apimeter.service.PlanResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/results")
public class PlanResultController {

    @Autowired
    PlanResultService planResultService;

    /*@GetMapping("/{threadId}/view")
    public String clear(Model model, @PathVariable("threadId") long threadId) {
        List<PlanResult> planResult = planResultService.findByThreadId(threadId);

        model.addAttribute("title", "Thread Response : " + threadId);
        model.addAttribute("pResults", planResult);

        return "/plan/thread/result/index";
    }*/

}
