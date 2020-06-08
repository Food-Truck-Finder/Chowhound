package com.chowhound.chowhound.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/about")
//    @ResponseBody
    public String hello() {
//        return "Hello from Spring!";
//    }

        return "/home";
    }
}
