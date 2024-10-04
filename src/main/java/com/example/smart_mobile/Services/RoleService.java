package com.example.smart_mobile.Services;

import com.example.smart_mobile.Models.Role;
import com.example.smart_mobile.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public List<Role> getAllExceptAdmin() {return roleRepository.findAllExceptAdmin();}

    public Role getRoleById(long id) {
        return roleRepository.findById(id).get();
    }
}
