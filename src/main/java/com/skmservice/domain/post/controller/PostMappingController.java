package com.skmservice.domain.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class PostMappingController {
    @GetMapping("/list")
    public String showRegisterPage() {
        return "postList";
    }

    @GetMapping("/add")
    public String RegisterPage() {
        return "postAdd";
    }
}
