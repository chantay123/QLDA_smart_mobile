package com.example.smart_mobile.Controllers.Admin;

import com.example.smart_mobile.Models.CartItems;
import com.example.smart_mobile.Models.Order;
import com.example.smart_mobile.Models.User;
import com.example.smart_mobile.Services.OrderService;
import com.example.smart_mobile.Services.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TransactionController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @GetMapping("/all-bills")
    public String viewBill(@NotNull Model model) {
        List<Order> orders = orderService.findAll();
        List<User> users = userService.getAllUsers();
        model.addAttribute("orders", orders);
        model.addAttribute("users", users);
        return "admin/TransactionsManagement";
    }
}
