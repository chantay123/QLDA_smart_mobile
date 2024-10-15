package com.example.smart_mobile.Controllers;

import com.example.smart_mobile.Models.Cart;
import com.example.smart_mobile.Models.CartItems;
import com.example.smart_mobile.Models.User;
import com.example.smart_mobile.Services.CartService;
import com.example.smart_mobile.Services.OrderService;
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
    @Autowired
    private OrderService orderService;

    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId) {
        Optional<User> user = userService.getUserAuthentication();
        cartService.addToCart(user.get().getId(), productId);
        return "redirect:/cart"; // Sau khi thêm vào giỏ, chuyển hướng đến trang giỏ hàng
    }

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

    @GetMapping
    public String viewCart(@NotNull Model model) {
        Optional<User> user = userService.getUserAuthentication();
        List<CartItems> items = cartService.getItemsInCart(user.get().getId());
        int totalPrice = cartService.calculateTotalPrice(user.get().getId());
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartItems", items);
        return "cart/cart";
    }
    @PostMapping("/check-out")
    public String checkOut(RedirectAttributes redirectAttributes) {
        Optional<User> user = userService.getUserAuthentication();
        if (user.isPresent()) {
            orderService.createOrder(user.get().getId());

            try {
                cartService.clearCart(user.get().getId());
                redirectAttributes.addFlashAttribute("message", "Giỏ hàng đã xuất hóa đơn và được xóa.");
            } catch (Exception e) {
                // Thêm thông báo nếu xảy ra lỗi trong quá trình xóa giỏ hàng
                redirectAttributes.addFlashAttribute("error", "Không thể xóa giỏ hàng: " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại.");
        }
        return "redirect:/cart";
    }

}
