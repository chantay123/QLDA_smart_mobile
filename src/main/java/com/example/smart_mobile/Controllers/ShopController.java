package com.example.smart_mobile.Controllers;

import com.example.smart_mobile.Models.*;
import com.example.smart_mobile.Services.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public String viewShop(
            @NotNull Model model,
            @RequestParam(value = "brands", required = false) List<Long> brandIds,
            @RequestParam(value = "priceRange", required = false) List<String> priceRanges,
            @RequestParam(name = "search", required = false) String search
    ) {
        Optional<User> user = userService.getUserAuthentication();
        List<CartItems> items = cartService.getItemsInCart(user.get().getId());
        List<Brand> brands = brandService.getAllBrands();
        List<Product> products = productService.filterProducts(brandIds, priceRanges);
        if(search != null && !search.isEmpty()) {
            products = productService.searchProductsByName(search);
        }
        else {
            if(brandIds != null && !brandIds.isEmpty()) {
                products = productService.filterProducts(brandIds,priceRanges);
            }
            else {
                products = productService.getAllProducts();
            }
        }
        model.addAttribute("brands", brands);
        model.addAttribute("products", products);
        model.addAttribute("cartItems", items);

        return "shop/shop";
    }

    @GetMapping("/product/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        Optional<User> user = userService.getUserAuthentication();
        List<CartItems> items = cartService.getItemsInCart(user.get().getId());
        model.addAttribute("cartItems", items);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "shop/productDetail";
        } else {
            // Handle product not found case
            return "redirect:/shop";
        }
    }

}
