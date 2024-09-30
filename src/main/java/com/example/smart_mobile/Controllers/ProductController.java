package com.example.smart_mobile.Controllers;

import com.example.smart_mobile.Entities.*;
import com.example.smart_mobile.Services.BrandService;
import com.example.smart_mobile.Services.ProductService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;

    @GetMapping("/list")
    public String getAllProducts(@NotNull Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "Admin/ProductManagement";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        List<Brand> brands = brandService.getAllBrands();
        model.addAttribute("brands", brands);
        return "product-add";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") Product product, @RequestParam("brandId") Long brandId) {
        productService.addProduct(product, brandId);
        return "redirect:/products/list";
    }
}
