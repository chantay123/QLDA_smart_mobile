package com.example.smart_mobile.Services;

import com.example.smart_mobile.Models.Role;
import com.example.smart_mobile.Models.User;
import com.example.smart_mobile.Repositories.RoleRepository;
import com.example.smart_mobile.Repositories.UserRepository;
import com.example.smart_mobile.Requests.UserRequest.CreateUser;
import com.example.smart_mobile.Requests.UserRequest.ModifyUser;
import com.example.smart_mobile.Requests.UserRequest.StatusAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public List<User> getAllUsers() {return userRepository.findAll();}

    public List<User> getAllUsersExceptAdmin(){return userRepository.findAllExceptAdmin();}

    public List<User> getUsersBaseOnIsDelete(Boolean isDelete){return userRepository.findByIsDelete(isDelete);}

    public List<User> searchUsers(String search){
        return userRepository.searchUsers(search);
    }

    public User getCurrentUser(long id) {return userRepository.findById(id).get();}

    public User createUser(CreateUser createUser) throws RuntimeException{
        User user = new User();

        user.setUsername(createUser.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(createUser.getPassword()));
        user.setEmail(createUser.getEmail());
        user.setPhoneNumber(createUser.getPhoneNumber());
        user.setBirth(createUser.getBirth());
        user.setAddress(createUser.getAddress());
        user.setIsDelete(false);
        user.setRoles(createUser.getRoles());

        return userRepository.save(user);
    }

    public User updateUser(ModifyUser modifyUser) throws RuntimeException{
        User user = userRepository.findById(modifyUser.getId()).get();
        user.setUsername(modifyUser.getUsername());
        user.setEmail(modifyUser.getEmail());
        user.setPhoneNumber(modifyUser.getPhoneNumber());
        user.setBirth(modifyUser.getBirth());
        user.setAddress(modifyUser.getAddress());
        user.setRoles(modifyUser.getRoles());
        return userRepository.save(user);
    }

    public User updateStatusAccount(StatusAccount statusAccount) throws RuntimeException{
        User user = userRepository.findById(statusAccount.getId()).get();
        user.setIsDelete(statusAccount.getIsDelete());
        return userRepository.save(user);
    }

    public User register(CreateUser createUser) {
        User user = new User();

        user.setUsername(createUser.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(createUser.getPassword()));
        user.setEmail(createUser.getEmail());
        user.setPhoneNumber(createUser.getPhoneNumber());
        user.setBirth(createUser.getBirth());
        user.setAddress(createUser.getAddress());
        user.setIsDelete(false);
        Role role = roleRepository.findById(roleRepository.findRoleIdByName(com.example.smart_mobile.Enums.Role.Customer.name()).get()).get();
        Set<Role> roles = Collections.singleton(role);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public Optional<User> getUserAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return userRepository.findByUsername(currentUserName);
    }

}
