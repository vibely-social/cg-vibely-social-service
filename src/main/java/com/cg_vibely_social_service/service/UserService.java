package com.cg_vibely_social_service.service;

import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.UserInfoRequestDto;
import com.cg_vibely_social_service.payload.request.UserLoginRequestDto;
import com.cg_vibely_social_service.payload.request.UserRegisterRequestDto;
import com.cg_vibely_social_service.payload.response.UserInfoResponseDto;
import com.cg_vibely_social_service.payload.response.UserLoginResponseDto;
import com.cg_vibely_social_service.payload.response.UserSuggestionResponseDto;

import java.util.List;


public interface UserService{
    void save(UserRegisterRequestDto userRegisterRequestDto);

    void update(Long id, User user);

    User findById(Long id);

    UserLoginResponseDto authenticate(UserLoginRequestDto userLoginRequestDto);

    String refreshToken();

    boolean checkValidEmail(String email);
    List<UserSuggestionResponseDto> find20UsersSuggestionByUserId(Long id);

    UserInfoResponseDto getUserInfoById (Long userId);

    void updateUserInfo(UserInfoRequestDto userInfoRequestDto);
    UserPrincipal getUserPrincipal(String email);
}
