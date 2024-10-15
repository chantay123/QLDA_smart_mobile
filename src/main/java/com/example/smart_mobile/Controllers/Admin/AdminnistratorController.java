package com.example.smart_mobile.Controllers.Admin;


import com.example.smart_mobile.Models.User;
import com.example.smart_mobile.Repositories.UserRepository;
import com.example.smart_mobile.Requests.UserRequest.CreateUser;
import com.example.smart_mobile.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminnistratorController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String usermanagement(@RequestParam(name = "isDelete", required = false) Boolean isDelete,
                                 @RequestParam(name = "search", required = false) String search,
                                 Model model){
        List<User> filteredUsers;

        if (isDelete == null) {
            filteredUsers = userService.getAllUsersExceptAdmin(); // Nếu không chọn checkbox nào, lấy tất cả tài khoản
        } else {
            filteredUsers = userService.getUsersBaseOnIsDelete(isDelete); // Lọc theo trạng thái khóa hoặc không khóa
        }

        if (search == null || search.isEmpty()) {
            filteredUsers = userService.getAllUsersExceptAdmin(); // Nếu không có tìm kiếm, lấy tất cả tài khoản
        } else {
            filteredUsers = userService.searchUsers(search); // Lọc theo tên người dùng hoặc email
        }

        model.addAttribute("isDelete", isDelete);
        model.addAttribute("search", search);
        model.addAttribute("users", filteredUsers);
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
}
