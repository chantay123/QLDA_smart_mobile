package com.example.smart_mobile.Repositories;

import com.example.smart_mobile.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name <> 'Admin'")
    List<User> findAllExceptAdmin();

    List<User> findByIsDelete(Boolean isDelete);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name <> 'Admin' AND (u.username LIKE %:keyword% OR u.email LIKE %:keyword%)")
    List<User> searchUsers(@Param("keyword") String keyword);

}
