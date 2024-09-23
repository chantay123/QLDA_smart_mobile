package com.example.smart_mobile.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminnistratorController {
    @GetMapping("/user")
    public String usermanagement(){
        return"admin/usermanagement";
    }
    @GetMapping("/product")
    public String productmanagement(){
        return"admin/productmanagement";
    }
    @GetMapping("/dashboard")
    public String dashboard(){
        return"admin/dashboard";
    }
    @GetMapping("/brand")
    public String brandmanagement(){
        return"admin/brandmanagement";
    }
    @GetMapping("/transaction")
    public String transactionmanagement(){
        return"admin/transactionsmanagement";
    }
}
