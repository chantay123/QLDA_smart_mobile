package com.example.smart_mobile.Controllers;

import com.example.smart_mobile.Models.CartItems;
import com.example.smart_mobile.Models.Order;
import com.example.smart_mobile.Models.OrderItems;
import com.example.smart_mobile.Models.User;
import com.example.smart_mobile.Services.CartService;
import com.example.smart_mobile.Services.OrderService;
import com.example.smart_mobile.Services.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bill")
public class OrderController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/your-bills")
    public String viewBill(@NotNull Model model) {
        Optional<User> user = userService.getUserAuthentication();
        List<CartItems> items = cartService.getItemsInCart(user.get().getId());
        List<Order> orders = orderService.getAllOrderByIdUser(user.get().getId());
        model.addAttribute("orders", orders);
        model.addAttribute("cartItems", items);
        return "cart/ListBill"; // Trả về trang hiển thị hóa đơn
    }

    @GetMapping("/your-bills/{orderId}")
    public String viewBillDetail(@PathVariable("orderId") Long orderId, Model model) {
        Optional<User> userOpt = userService.getUserAuthentication();
        User user = userOpt.get();
        List<CartItems> items = cartService.getItemsInCart(user.getId());
        List<OrderItems> orderItems = orderService.getAllItemsByOrderId(orderId);
        Order order = orderService.getOrderById(orderId);
        model.addAttribute("user", user);
        model.addAttribute("order", order);
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("cartItems", items);
        return "cart/bill";
    }

    @GetMapping("/pay")
    public String payForOrder(@RequestParam("extraData") String orderId) {
        orderService.payOrder(Long.parseLong(orderId));
        return "redirect:/bill/your-bills";
    }

}
