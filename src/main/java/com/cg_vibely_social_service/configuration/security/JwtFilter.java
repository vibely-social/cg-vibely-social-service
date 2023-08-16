package com.cg_vibely_social_service.configuration.security;

import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorizeHeader = request.getHeader("Authorization");
            String email = null;
            String jwtToken = null;
            String token = null;

            Cookie[] cookies = request.getCookies();
            if(cookies!=null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("__vibely")) {
                        token = cookie.getValue();
                    }
                }
            }

            if ((authorizeHeader == null && token == null)
                    || Objects.equals(token, "null")
                    || Objects.equals(token, "undefined")) {
                filterChain.doFilter(request, response);
                return;
            }
            if (token != null) {
                jwtToken = token;
            }
            else {
                if (authorizeHeader.startsWith("Bearer")) {
                    jwtToken = authorizeHeader.substring(7);
                } else {
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            email = jwtUtil.extractEmail(jwtToken);
            if (email != null) {
                User user = (User) userService.loadUserByUsername(email);
                if (user != null) {
                    if (jwtUtil.isTokenValid(jwtToken)) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        request.getSession().setAttribute("currentUser", user);
                    } else {
                        request.getSession().setAttribute("currentUser", null);
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("Unauthorized: Authentication failed");
                    }
                }
            }
        } catch (ExpiredJwtException exception) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token expired\"}");
            return;
        }
        filterChain.doFilter(request, response);
    }
}

