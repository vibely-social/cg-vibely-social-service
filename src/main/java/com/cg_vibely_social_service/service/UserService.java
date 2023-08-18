package com.cg_vibely_social_service.service;

import com.cg_vibely_social_service.payload.request.UserLoginRequestDto;
import com.cg_vibely_social_service.payload.request.UserRegisterRequestDto;
import com.cg_vibely_social_service.payload.response.UserLoginResponseDto;
import com.cg_vibely_social_service.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void save(UserRegisterRequestDto userRegisterRequestDto);

    void update(Long id, User user);

    User findById(Long id);

    UserLoginResponseDto authenticate(UserLoginRequestDto userLoginRequestDto);

    String refreshToken(String bearerToken);

    boolean checkValidEmail(String email);

    User findByEmail(String email);
}
