package com.example.smart_mobile.Services;

import com.example.smart_mobile.Models.Product;
import com.example.smart_mobile.Repositories.ProductRepository;
import com.example.smart_mobile.Models.Brand;
import com.example.smart_mobile.Repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    public List<Product> searchProductsByName(String name) {
        return  productRepository.findTop3ByNameContaining(name);
    }

    public Product addProduct(Product product, Long brandId) {
        Optional<Brand> brand = brandRepository.findById(brandId);
        if (brand.isPresent()) {
            product.setBrand(brand.get());
            return productRepository.save(product);
        } else {
            throw new RuntimeException("Brand not found with id: " + brandId);
        }
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setImageUrl(updatedProduct.getImageUrl());
            product.setPrice(updatedProduct.getPrice());
            product.setStockQuantity(updatedProduct.getStockQuantity());
            product.setStorageCapacity(updatedProduct.getStorageCapacity());
            product.setColor(updatedProduct.getColor());
            product.setInstallmentPeriod(updatedProduct.getInstallmentPeriod());
            product.setMonthlyPayment(updatedProduct.getMonthlyPayment());
            product.setProcessor(updatedProduct.getProcessor());
            product.setScreenSize(updatedProduct.getScreenSize());
            product.setReleaseDate(updatedProduct.getReleaseDate());
            return productRepository.save(product);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getTopThreeIPhones() {
        return productRepository.findTop3ByNameContaining("iPhone");
    }
    public List<Product> filterProducts(List<Long> brandIds, List<String> priceRanges) {
        // Xử lý lọc theo thương hiệu và khoảng giá
        if (brandIds == null && priceRanges == null) {
            // Nếu không có tham số lọc, trả về tất cả sản phẩm
            return productRepository.findAll();
        }

        // Chuyển đổi giá trị của khoảng giá sang dạng số
        BigDecimal minPrice = null;
        BigDecimal maxPrice = null;
        if (priceRanges != null && !priceRanges.isEmpty()) {
            String[] range = priceRanges.get(0).split("-");
            minPrice = new BigDecimal(range[0]);
            maxPrice = range.length > 1 ? new BigDecimal(range[1]) : null;
        }

        // Gọi phương thức lọc sản phẩm theo tiêu chí từ repository
        return productRepository.findProductsByBrandAndPrice(brandIds, minPrice, maxPrice);
    }
}
