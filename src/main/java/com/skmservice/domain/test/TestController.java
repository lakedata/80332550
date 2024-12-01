package com.skmservice.domain.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    @RequestMapping(value = "/test")
    public String test(Model model) {
        model.addAttribute("message", "반갑습니다^^");
        return "test.html";  // Thymeleaf 템플릿을 리턴
    }
}
