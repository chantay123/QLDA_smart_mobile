package com.example.smart_mobile.Controllers.Admin;


import com.example.smart_mobile.Models.Order;
import com.example.smart_mobile.Models.User;
import com.example.smart_mobile.Repositories.UserRepository;
import com.example.smart_mobile.Requests.UserRequest.CreateUser;
import com.example.smart_mobile.Services.OrderService;
import com.example.smart_mobile.Services.UserService;
import jakarta.validation.constraints.NotNull;
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
    @Autowired
    private OrderService orderService;

    @GetMapping("/user")
    public String usermanagement(@RequestParam(name = "isDelete", required = false) Boolean isDelete,
                                         @RequestParam(name = "search", required = false) String search,
                                 Model model){
        List<User> filteredUsers;

        if (search != null) {
            filteredUsers = userService.searchUsers(search); // Lọc theo tên người dùng hoặc email
        } else {
            if (isDelete != null) {
                filteredUsers = userService.getUsersBaseOnIsDelete(isDelete); // Lọc theo trạng thái khóa hoặc không khóa
            } else {
                filteredUsers = userService.getAllUsersExceptAdmin(); // Nếu không chọn checkbox nào, lấy tất cả tài khoản
            }
        }

        model.addAttribute("isDelete", isDelete);
        model.addAttribute("search", search);
        model.addAttribute("users", filteredUsers);
        return"admin/usermanagement";
    }

    @GetMapping("")
    public String dashboard(@NotNull Model model){
        List<Order> orders = orderService.findAll();
        List<User> users = userService.getAllUsersExceptAdmin();
        long numberOfUser = userService.getUserCount();
        long numberOfOrders = orderService.getOrderCount();
        model.addAttribute("numberOfOrders", numberOfOrders);
        model.addAttribute("numberOfUser", numberOfUser);
        model.addAttribute("orders", orders);
        model.addAttribute("users", users);
        return"admin/dashboard";
    }
}
