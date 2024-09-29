package com.example.smart_mobile.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Builder
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // Đảm bảo tính duy nhất
    private String name;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<User> users = new HashSet<>();
}