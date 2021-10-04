package com.fornite.apimeter.controller;

import com.fornite.apimeter.helper.HttpHelper;
import com.fornite.apimeter.helper.HttpParam;
import com.fornite.apimeter.helper.HttpResults;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class WelcomeController {

    @GetMapping("")
    public String welcome() {
        //getExample();
        //postExample();
        return "/welcome";
    }

    private void getExample() {
        String title = "WebHookSite";
        String url = "https://jsonplaceholder.typicode.com/users/3";

        HttpParam httpParam = new HttpParam(title, url, "");

        HttpResults result = HttpHelper.requestByGet(httpParam, headerSet -> {
            //headerSet.setHeader("Auth", "123456");
            //headerSet.setHeader("Key", "678901");
            return headerSet;
        });

        log.info("response: {}", result);
    }

    private void postExample() {
        String title = "WebHookSite";
        String url = "https://jsonplaceholder.typicode.com/users";

        JSONObject data = new JSONObject();
        data.put("item", "Silver Queen");
        data.put("price", "15800");

        HttpParam httpParam = new HttpParam(title, url, data.toString());

        HttpResults result = HttpHelper.requestByPost(httpParam, headerSet -> {
            headerSet.setHeader("Auth", "123456");
            headerSet.setHeader("Key", "678901");
            return headerSet;
        });

        log.info("response: {}", result);
    }


}
