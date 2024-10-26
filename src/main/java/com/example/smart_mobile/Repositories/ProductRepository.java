package com.example.smart_mobile.Repositories;

import com.example.smart_mobile.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findTop3ByNameContaining(String name);
    @Query("SELECT p FROM Product p WHERE "
            + "( :brandIds IS NULL OR p.brand.id IN :brandIds ) "
            + "AND ( :minPrice IS NULL OR p.price >= :minPrice ) "
            + "AND ( :maxPrice IS NULL OR p.price <= :maxPrice )")
    List<Product> findProductsByBrandAndPrice(@Param("brandIds") List<Long> brandIds,
                                              @Param("minPrice") BigDecimal minPrice,
                                              @Param("maxPrice") BigDecimal maxPrice);


}

