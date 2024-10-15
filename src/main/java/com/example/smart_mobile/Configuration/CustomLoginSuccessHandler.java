package com.example.smart_mobile.Configuration;

import com.example.smart_mobile.Enums.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Collection<?> authorities = authentication.getAuthorities();

        // Điều hướng tùy vào quyền hạn
        if (authorities.toString().contains(Role.Admin.name())) {
            response.sendRedirect("/admin");
        } else {
            response.sendRedirect("/");
        }
    }
}
