package com.cg_vibely_social_service.converter.impl;

import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.response.FriendResponseDto;
import com.cg_vibely_social_service.payload.response.UserSearchResponseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserSearchResponseDtoConverter implements Converter<UserSearchResponseDto, User> {
    public UserSearchResponseDtoConverter() {
        super();
    }

    @Override
    public User convert(UserSearchResponseDto source) {
        return null;
    }

    @Override
    public UserSearchResponseDto revert(User target) {
        UserSearchResponseDto result = new UserSearchResponseDto();
        BeanUtils.copyProperties(target, result);
        return result;
    }

    @Override
    public List<User> convert(List<UserSearchResponseDto> sources) {
        return null;
    }

    @Override
    public List<UserSearchResponseDto> revert(List<User> targets) {
        return targets.stream()
                .map(this::revert)
                .toList();
    }
}
