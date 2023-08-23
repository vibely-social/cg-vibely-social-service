package com.cg_vibely_social_service.converter.impl;

import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserResponseDtoConverter implements Converter<UserResponseDto, User> {
    @Override
    public User convert(UserResponseDto source) {
        return null;
    }

    @Override
    public UserResponseDto revert(User target) {
        return UserResponseDto.builder()
                .id(target.getId())
                .firstName(target.getFirstName())
                .lastName(target.getLastName())
                .avatar(target.getAvatar())
                .build();
    }

    @Override
    public List<User> convert(List<UserResponseDto> sources) {
        return null;
    }

    @Override
    public List<UserResponseDto> revert(List<User> targets) {
        return null;
    }
}
