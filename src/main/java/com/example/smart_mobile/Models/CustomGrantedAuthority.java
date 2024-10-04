package com.example.smart_mobile.Models;

import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

public class CustomGrantedAuthority implements GrantedAuthority {

    private Role role;

    @Override
    public String getAuthority() {
        return role.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (role == o) return true;
        if (o == null || Hibernate.getClass(role) != Hibernate.getClass(o)) return
                false;
        Role newrole = (Role) o;
        return role.getId() != null && Objects.equals(role.getId(), newrole.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
