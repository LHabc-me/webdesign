package com.web_design.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWord {
    @RequestMapping("/helloworld")
    public String helloWorld() {
        return "hello world!";
    }
}
