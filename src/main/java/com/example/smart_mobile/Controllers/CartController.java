package com.example.smart_mobile.Controllers;

import com.example.smart_mobile.Models.Cart;
import com.example.smart_mobile.Models.CartItems;
import com.example.smart_mobile.Models.User;
import com.example.smart_mobile.Services.CartService;
import com.example.smart_mobile.Services.ProductService;
import com.example.smart_mobile.Services.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId) {
        Optional<User> user = userService.getUserAuthentication();
        cartService.addToCart(user.get().getId(), productId);
        return "redirect:/cart"; // Sau khi thêm vào giỏ, chuyển hướng đến trang giỏ hàng
    }

    /**
     * Xóa sản phẩm khỏi giỏ hàng
     */
    @PostMapping("/remove/{productId}")
    public String removeProductFromCart(@PathVariable Long productId, RedirectAttributes redirectAttributes) {
        Optional<User> user = userService.getUserAuthentication();
        if (user.isPresent()) {
            System.out.println("Current User Roles: " + user.get().getRoles());
            cartService.removeFromCart(user.get().getId(), productId);
            redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được xóa khỏi giỏ hàng");
        } else {
            redirectAttributes.addFlashAttribute("error", "Người dùng không xác thực");
        }
        return "redirect:/cart";
    }
    /**
     * Hiển thị giỏ hàng
     */
    @GetMapping
    public String viewCart(@NotNull Model model) {
        Optional<User> user = userService.getUserAuthentication();
        List<CartItems> items = cartService.getItemsInCart(user.get().getId());
        model.addAttribute("cartItems", items);
        return "cart/cart"; // Tên template Thymeleaf để hiển thị giỏ hàng
    }
    @PostMapping("/clear")
    public String clearCart(RedirectAttributes redirectAttributes) {
        Optional<User> user = userService.getUserAuthentication();
        if (user.isPresent()) {
            cartService.clearCart(user.get().getId());
            redirectAttributes.addFlashAttribute("message", "Giỏ hàng đã được xóa.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại.");
        }
        return "redirect:/cart"; // Chuyển hướng về trang giỏ hàng
    }
}
