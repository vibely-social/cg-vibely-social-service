package com.cg_vibely_social_service.configuration.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        String errorMessage = "Access denied!!!";
        String errorMessage = authException.getMessage();
        authException.printStackTrace();

        if (authException.getMessage().contains("JWT expired")) {
            errorMessage = "Access token expired";
        }

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + errorMessage + "\"}");
    }
}
