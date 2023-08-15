package com.cg_vibely_social_service.converter.impl;

import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.response.FriendResponseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FriendResponseDtoConverter implements Converter<FriendResponseDto, User> {
    @Override
    public User convert(FriendResponseDto source) {
        return null;
    }

    @Override
    public List<User> convert(List<FriendResponseDto> sources) {
        return null;
    }

    @Override
    public FriendResponseDto revert(User target) {
        FriendResponseDto result = new FriendResponseDto();
        BeanUtils.copyProperties(target, result);
        return result;
    }

    @Override
    public List<FriendResponseDto> revert(List<User> targets) {
        return targets.stream()
                .map(this::revert)
                .toList();
    }
}
