package com.skmservice.domain.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/Index")
    public String showLoginPage() {
        return "Index";
    }

}
