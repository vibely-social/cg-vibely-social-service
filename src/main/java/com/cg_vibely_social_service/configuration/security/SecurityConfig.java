package com.cg_vibely_social_service.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final JwtFilter jwtFilter;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //permit all for easy testing, will disable in production
        http.authorizeHttpRequests()
                .requestMatchers("/api/**")
                .permitAll();

        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/api/users")
                .permitAll();

        http.authorizeHttpRequests()
                .requestMatchers("/api/auth/login/")
                .permitAll();

        http.authorizeHttpRequests()
                .requestMatchers("/api/auth/refreshtoken")
                .permitAll();


        //Testing random request
        http.authorizeHttpRequests()
                .requestMatchers("/api/auth/random")
                .permitAll();

        //This is for testing filter by role
        http.authorizeHttpRequests()
                .requestMatchers("/api/auth/admin")
                .hasRole("ADMIN");

        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf()
                .ignoringRequestMatchers("/api/**");


        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
