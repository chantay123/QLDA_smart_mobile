package com.example.smart_mobile.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "không được bỏ trống")
    @Column(unique = true) // Đảm bảo tính duy nhất
    private String username;
    private String password;
    @Column(unique = true) // Đảm bảo tính duy nhất
    private String email;
    @Column(unique = true) // Đảm bảo tính duy nhất
    private String phoneNumber;
    private Date birth;
    private String address;
    private Boolean isDelete;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
