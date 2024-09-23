package com.example.smart_mobile.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String home() {
        return "cart/cart";
    }

    @GetMapping("/login")
    public String login() {
        return "Login/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "Login/signup";
    }
}