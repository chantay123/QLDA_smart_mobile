package com.example.smart_mobile.Controllers;


import com.example.smart_mobile.Requests.UserRequest.CreateUser;
import com.example.smart_mobile.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminnistratorController {
    private final UserService userService;

    public AdminnistratorController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String usermanagement(){
        return"admin/usermanagement";
    }

    @GetMapping("")
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

    @GetMapping("/createuser")
    public String createuser(Model model){
        model.addAttribute("user",new CreateUser());
        return"admin/usercreate";
    }

    @PostMapping("/createUser")
    public String createUser(CreateUser user) {
        // Trả về trang xác nhận hoặc redirect
        userService.createUser(user); // Trả về tên của view hoặc redirect đến trang khác
        return "redirect:/admin";
    }
}
