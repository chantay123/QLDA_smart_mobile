package com.example.smart_mobile.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/")
public class HomeController {


    @GetMapping("/shop")
    public String shop() { return "Shop/shop"; }
}