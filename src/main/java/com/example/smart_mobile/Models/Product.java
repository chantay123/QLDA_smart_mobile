package com.example.smart_mobile.Models;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import jakarta.validation.constraints.*;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 50, message = "Product name must be between 2 and 50 characters")
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    @Lob
    private String description;

    @NotBlank(message = "Image URL is required")
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "is_delete", nullable = false)
    private boolean isDelete = false; // Default to false

    @Min(value = 0, message = "Price must be a positive value")
    @Column(name = "price", nullable = false)
    private int price;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @Column(name = "storage_capacity", nullable = true)
    private String storageCapacity;

    @Column(name = "color", nullable = true)
    private String color;

    @Min(value = 0, message = "Installment period must be positive")
    @Column(name = "installment_period", nullable = true)
    private Integer installmentPeriod;

    @DecimalMin(value = "0.0", inclusive = false, message = "Monthly payment must be greater than 0")
    @Column(name = "monthly_payment", nullable = true)
    private Double monthlyPayment;

    @Column(name = "processor", nullable = true)
    private String processor;

    @Column(name = "screen_size", nullable = true)
    private String screenSize;

    @Column(name = "release_date", nullable = true)
    private LocalDate releaseDate;

    @ManyToOne
    @NotNull(message = "Brand is required")
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;
}