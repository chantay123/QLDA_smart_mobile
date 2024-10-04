package com.example.smart_mobile.Services;

import com.example.smart_mobile.Models.CustomUserDetails;
import com.example.smart_mobile.Models.User;
import com.example.smart_mobile.Repositories.RoleRepository;
import com.example.smart_mobile.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            var userObj = new CustomUserDetails(user.get());
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .authorities(userObj.getAuthorities())
                    .accountExpired(!userObj.isAccountNonExpired())
                    .accountLocked(userObj.getIsDelete())
                    .credentialsExpired(!userObj.isCredentialsNonExpired())
                    .disabled(!userObj.isEnabled())
                    .build();
        }
        else
        {
            throw new UsernameNotFoundException("Account does not exist.");
        }
    }
}
