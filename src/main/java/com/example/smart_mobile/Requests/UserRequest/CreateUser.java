package com.example.smart_mobile.Requests.UserRequest;


import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;

@Getter
@Setter
public class CreateUser {
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    private Date birth;
    private String address;
    private Boolean isDelete;
    private Set<String> roles;
}
