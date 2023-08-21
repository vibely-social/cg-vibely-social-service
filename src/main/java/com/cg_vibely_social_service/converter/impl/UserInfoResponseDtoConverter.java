package com.cg_vibely_social_service.converter.impl;

import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.response.UserInfoResponseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class UserInfoResponseDtoConverter implements Converter<UserInfoResponseDto, User> {

    @Override
    public User convert(UserInfoResponseDto source) {
        return null;
    }

    @Override
    public List<User> convert(List<UserInfoResponseDto> sources) {
        return null;
    }

    @Override
    public UserInfoResponseDto revert(User target) {
        UserInfoResponseDto result = new UserInfoResponseDto();
        BeanUtils.copyProperties(target, result);
        result.setBirthday(target.getDayOfBirth());
        return result;
    }

    @Override
    public List<UserInfoResponseDto> revert(List<User> targets) {
        return targets.stream()
                .map(this::revert)
                .toList();
    }
}
