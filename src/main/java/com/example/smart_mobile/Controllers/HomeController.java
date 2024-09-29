package com.example.smart_mobile.Controllers;

import com.example.smart_mobile.Repositories.UserRepository;
import com.example.smart_mobile.Requests.UserRequest.CreateUser;
import com.example.smart_mobile.Services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping()
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "cart/cart";
    }

    @GetMapping("/login")
    public String login() {
        return "Login/login";
    }

    @GetMapping("/signup")
    public String signup(@NotNull Model model) {
        model.addAttribute("user", new CreateUser());
        return "Login/signup";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") CreateUser user, // Validate đối tượng User
                           @NotNull BindingResult bindingResult, // Kết quả của quá trình validate
                           Model model){
        if (bindingResult.hasErrors()) { // Kiểm tra nếu có lỗi validate
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "login/signup"; // Trả về lại view "register" nếu có lỗi
        }
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            model.addAttribute("error", "user already exists");
            return "login/signup";
        }
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            model.addAttribute("error", "Email already exists");
            return "login/signup";
        }
        if(userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent()){
            model.addAttribute("error", "Phone number already exists");
            return "login/signup";
        }
        userService.register(user); // Lưu người dùng vào cơ sở dữ liệu
        return "redirect:/login"; // Chuyển hướng người dùng tới trang "login"
    }
}