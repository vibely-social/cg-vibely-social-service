package com.cg_vibely_social_service.service;

import com.cg_vibely_social_service.dto.request.RequestLoginDto;
import com.cg_vibely_social_service.dto.request.RequestRegisterDto;
import com.cg_vibely_social_service.dto.response.ResponseLoginDto;
import com.cg_vibely_social_service.dto.response.ResponseRegisterDto;
import com.cg_vibely_social_service.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService, GenericService<User> {
    ResponseLoginDto authenticate(RequestLoginDto requestLoginDto);

    ResponseRegisterDto register(RequestRegisterDto requestRegisterDto);
}
