package com.cg_vibely_social_service.service;

import com.cg_vibely_social_service.payload.request.LoginRequestDto;
import com.cg_vibely_social_service.payload.response.LoginResponseDto;
import com.cg_vibely_social_service.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void save(User user);

    void update(Long id, User user);

    User findById(Long id);

    LoginResponseDto authenticate(LoginRequestDto loginRequestDto);

    LoginResponseDto refreshToken(String token);

    boolean checkValidEmail(String email);
}
