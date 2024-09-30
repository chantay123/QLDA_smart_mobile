package com.example.smart_mobile.Services;

import com.example.smart_mobile.Entities.Brand;
import com.example.smart_mobile.Repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public Brand addBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public Brand updateBrand(Long id, Brand updatedBrand) {
        Optional<Brand> existingBrand = brandRepository.findById(id);
        if (existingBrand.isPresent()) {
            Brand brand = existingBrand.get();
            brand.setName(updatedBrand.getName());
            brand.setImageUrl(updatedBrand.getImageUrl());
            brand.setDescription(updatedBrand.getDescription());
            return brandRepository.save(brand);
        } else {
            throw new RuntimeException("Brand not found with id: " + id);
        }
    }

    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Optional<Brand> getBrandById(Long id) {
        return brandRepository.findById(id);
    }
}
