package com.example.smart_mobile.Configuration;

import com.example.smart_mobile.Models.Role;
import com.example.smart_mobile.Models.User;
import com.example.smart_mobile.Repositories.RoleRepository;
import com.example.smart_mobile.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository){
        return args -> {
            if(roleRepository.findByName(com.example.smart_mobile.Enums.Role.Admin.name()).isEmpty()){
                Role role = Role.builder()
                        .name(com.example.smart_mobile.Enums.Role.Admin.name())
                        .build();
                roleRepository.save(role);
            }
            if(roleRepository.findByName(com.example.smart_mobile.Enums.Role.Employee.name()).isEmpty()){
                Role role = Role.builder()
                        .name(com.example.smart_mobile.Enums.Role.Employee.name())
                        .build();
                roleRepository.save(role);
            }
            if(roleRepository.findByName(com.example.smart_mobile.Enums.Role.Customer.name()).isEmpty()){
                Role role = Role.builder()
                        .name(com.example.smart_mobile.Enums.Role.Customer.name())
                        .build();
                roleRepository.save(role);
            }
            if(userRepository.findByUsername("admin").isEmpty()){
                Role role = roleRepository.findById(roleRepository.findRoleIdByName(com.example.smart_mobile.Enums.Role.Admin.name()).get()).get();
                Set<Role> roles = Collections.singleton(role);
                User user = User.builder()
                            .username("admin")
                            .password(passwordEncoder.encode("admin"))
                            .roles(roles)
                            .build();
                userRepository.save(user);
                log.warn("admin được khởi tạo với mật khẩu là: admin");
            }
        };
    }
}
