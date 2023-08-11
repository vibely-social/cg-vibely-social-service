package com.vibely_social.configuration.security;

import com.vibely_social.configuration.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/*", corsConfig);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Permit all for test, will disable in production
        http.authorizeHttpRequests()
                .requestMatchers("/api/**")
                .permitAll();

        http.authorizeHttpRequests()
                .requestMatchers("/api/auth/login")
                .permitAll();

        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/api/users")
                .permitAll();


        //This is for testing filter by role
        http.authorizeHttpRequests()
                .requestMatchers("/api/auth/admin")
                .hasRole("ADMIN");

        http.cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf()
                .ignoringRequestMatchers("/api/**");

        //add function to use api
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/api/posts")
                .permitAll();
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/api/**")
                .permitAll();
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.PUT, "/api/**")
                .permitAll();


        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
