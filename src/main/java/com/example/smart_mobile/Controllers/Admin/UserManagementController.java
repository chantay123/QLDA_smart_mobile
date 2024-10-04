package com.example.smart_mobile.Controllers.Admin;

import com.example.smart_mobile.Models.User;
import com.example.smart_mobile.Repositories.UserRepository;
import com.example.smart_mobile.Requests.UserRequest.CreateUser;
import com.example.smart_mobile.Requests.UserRequest.ModifyUser;
import com.example.smart_mobile.Requests.UserRequest.StatusAccount;
import com.example.smart_mobile.Services.RoleService;
import com.example.smart_mobile.Services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class UserManagementController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/createuser")
    public String createuser(Model model){
        model.addAttribute("user",new CreateUser());
        model.addAttribute("roles", roleService.getAllExceptAdmin());
        return"admin/usercreate";
    }

    @PostMapping("/createUser")
    public String createUser(@Valid @ModelAttribute("user") CreateUser user, // Validate đối tượng User
                             @NotNull BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) { // Kiểm tra nếu có lỗi validate
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "admin/usercreate"; // Trả về lại view "register" nếu có lỗi
        }
        // Trả về trang xác nhận hoặc redirect
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            model.addAttribute("error", "user already exists");
            return "admin/usercreate";
        }
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            model.addAttribute("error", "Email already exists");
            return "admin/usercreate";
        }
        if(userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent()){
            model.addAttribute("error", "Phone number already exists");
            return "admin/usercreate";
        }
        userService.createUser(user); // Trả về tên của view hoặc redirect đến trang khác
        return "redirect:/admin/user";
    }

    @GetMapping("/viewuser/{id}")
    public String viewUser(@PathVariable Long id, Model model){
        model.addAttribute("user", userService.getCurrentUser(id));
        model.addAttribute("roles", roleService.getAllExceptAdmin());
        return"admin/viewuser";
    }

    @GetMapping("/modifyuser/{id}")
    public String modifyUser(@PathVariable Long id, Model model){
        model.addAttribute("user", userService.getCurrentUser(id));
        model.addAttribute("roles", roleService.getAllExceptAdmin());
        return"admin/usermodify";
    }

    @PostMapping("/userupdate")
    public String updateUser(@Valid @ModelAttribute("user") ModifyUser modifyUser, // Validate đối tượng User
                             @NotNull BindingResult bindingResult,
                             Model model){
        model.addAttribute("roles", roleService.getAllExceptAdmin());
        if (bindingResult.hasErrors()) { // Kiểm tra nếu có lỗi validate
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "admin/usermodify"; // Trả về lại view "register" nếu có lỗi
        }
        if(userRepository.findByUsername(modifyUser.getUsername()).isPresent() &&
                !userRepository.findByUsername(modifyUser.getUsername()).get().getId().equals(modifyUser.getId())
        ){
            model.addAttribute("error", "user already exists");
            return "admin/usermodify";
        }
        if(userRepository.findByEmail(modifyUser.getEmail()).isPresent() &&
                !userRepository.findByEmail(modifyUser.getEmail()).get().getId().equals(modifyUser.getId())
        ){
            model.addAttribute("error", "Email already exists");
            return "admin/usermodify";
        }
        if(userRepository.findByPhoneNumber(modifyUser.getPhoneNumber()).isPresent() &&
                !userRepository.findByPhoneNumber(modifyUser.getPhoneNumber()).get().getId().equals(modifyUser.getId())
        ){
            model.addAttribute("error", "Phone number already exists");
            return "admin/usermodify";
        }
        userService.updateUser(modifyUser);
        return "redirect:/admin/viewuser/" + modifyUser.getId();
    }

    @PostMapping("/updateUserStatus")
    public String updateUser(@Valid @ModelAttribute("user") StatusAccount statusAccount, // Validate đối tượng User
                             @NotNull BindingResult bindingResult,
                             Model model) {
        model.addAttribute("roles", roleService.getAllExceptAdmin());
        if (bindingResult.hasErrors()) { // Kiểm tra nếu có lỗi validate
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "admin/usermodify"; // Trả về lại view "register" nếu có lỗi
        }
        userService.updateStatusAccount(statusAccount);
        return "redirect:/admin/viewuser/" + statusAccount.getId();
    }



}
