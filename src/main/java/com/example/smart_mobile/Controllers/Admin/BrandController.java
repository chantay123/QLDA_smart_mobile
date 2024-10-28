package com.example.smart_mobile.Controllers.Admin;

import com.example.smart_mobile.Models.Brand;
import com.example.smart_mobile.Services.BrandService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/brands")
    public String listBrands(@NotNull Model model) {
        List<Brand> brands = brandService.getAllBrands();
        model.addAttribute("brands", brands);
        return "admin/BrandManagement";
    }

    @GetMapping("/brands/add")
    public String showAddForm(@NotNull Model model) {
        model.addAttribute("brand", new Brand());
        return "admin/BrandForm"; // Form thêm mới sẽ được hiển thị
    }

    @PostMapping("/brands/add")
    public String addBrand(@ModelAttribute Brand brand) {
        brandService.addBrand(brand);
        return "redirect:/admin/brands"; // Chuyển hướng về danh sách brand sau khi thêm
    }

    @GetMapping("/brands/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Brand brand = brandService.getBrandById(id).orElseThrow(() -> new RuntimeException("Brand not found"));
        model.addAttribute("brand", brand);
        return "admin/BrandForm"; // Hiển thị form chỉnh sửa
    }

    @PostMapping("/brands/edit/{id}")
    public String updateBrand(@PathVariable Long id, @ModelAttribute Brand brand) {
        brandService.updateBrand(id, brand);
        return "redirect:/admin/brands"; // Chuyển hướng về danh sách brand sau khi cập nhật
    }

    @GetMapping("/brands/delete/{id}")
    public String deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return "redirect:/admin/brands"; // Chuyển hướng về danh sách brand sau khi xóa
    }
}
