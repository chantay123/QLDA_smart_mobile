package com.example.smart_mobile.Configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.net.URLEncoder;

import java.util.Base64;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "";

        // Kiểm tra lỗi và thiết lập thông báo lỗi
        if (exception instanceof LockedException) {
            errorMessage = "Account has been locked.";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "Incorrect username or password";
        }
        // Sử dụng Hexadecimal để mã hóa thông báo lỗi
        String encodedErrorMessage = hexEncode(errorMessage);

        // Chuyển hướng đến trang đăng nhập cùng với thông báo lỗi
        response.sendRedirect("/login?error=" + encodedErrorMessage);
    }

    private String hexEncode(String message) {
        StringBuilder hexString = new StringBuilder();
        for (char ch : message.toCharArray()) {
            hexString.append(String.format("%02x", (int) ch));
        }
        return hexString.toString();
    }
}

