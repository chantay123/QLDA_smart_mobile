package com.example.smart_mobile.Controllers;

import com.example.smart_mobile.Models.Brand;
import com.example.smart_mobile.Models.Product;
import com.example.smart_mobile.Services.BrandService;
import com.example.smart_mobile.Services.ProductService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "product/addProduct";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") Product product, @RequestParam("brandId") Long brandId, Model model, RedirectAttributes redirectAttributes) {
        Brand brand = brandService.getBrandById(brandId).orElse(null);
        product.setBrand(brand);
        try {
            redirectAttributes.addFlashAttribute("successMessage", "Thêm sản phẩm thành công!");
            productService.addProduct(product, brandId);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Đã xảy ra lỗi trong quá trình thêm sản phẩm: " + e.getMessage());
            return "product/addProduct";
        }
        return "redirect:/products/list";
    }


    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        model.addAttribute("product", product);
        List<Brand> brands = brandService.getAllBrands();
        model.addAttribute("brands", brands);
        return "product/editProduct";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute("product") Product updatedProduct, @RequestParam("brandId") Long brandId, RedirectAttributes redirectAttributes) {
        productService.updateProduct(id, updatedProduct);
        updatedProduct.setBrand(brandService.getBrandById(brandId).orElse(null));
        redirectAttributes.addFlashAttribute("successMessage", "Sửa sản phẩm thành công!");
        return "redirect:/products/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa sản phẩm thành công!");
        return "redirect:/products/list";
    }

    @GetMapping("/{id}")
    public String getProductDetails(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        model.addAttribute("product", product);
        return "product/productDetails"; // Assuming you have a productDetails.html template
    }
}
