package com.example.smart_mobile.Models;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    private int total;

    private String address;

    private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItems> orderItems;
}

