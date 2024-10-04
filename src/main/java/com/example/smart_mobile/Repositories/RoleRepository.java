package com.example.smart_mobile.Repositories;

import com.example.smart_mobile.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.name <> 'Admin'")
    List<Role> findAllExceptAdmin();

    @Query("SELECT r.id FROM Role r WHERE r.name = :name")
    Optional<Long> findRoleIdByName(String name);
    Optional<Role> findByName(String name);
}
