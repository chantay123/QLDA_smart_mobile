package com.example.smart_mobile.Services;

import com.example.smart_mobile.Models.Product;
import com.example.smart_mobile.Repositories.ProductRepository;
import com.example.smart_mobile.Models.Brand;
import com.example.smart_mobile.Repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

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
}
