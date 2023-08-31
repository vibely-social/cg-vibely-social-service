package com.cg_vibely_social_service.converter.impl;

import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.entity.FriendRequest;
import com.cg_vibely_social_service.payload.request.FriendRequestRequestDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class FriendRequestDtoConverter implements Converter<FriendRequest, FriendRequestRequestDto> {
    @Override
    public FriendRequestRequestDto convert(FriendRequest source) {
        FriendRequestRequestDto friendRequestRequestDto = new FriendRequestRequestDto();
        BeanUtils.copyProperties(source, friendRequestRequestDto);
        return friendRequestRequestDto;
    }

    @Override
    public List<FriendRequestRequestDto> convert(List<FriendRequest> sources) {
        return sources.stream()
                .map(this::convert)
                .toList();
    }

    @Override
    public FriendRequest revert(FriendRequestRequestDto target) {
        FriendRequest friendRequest = new FriendRequest();
        BeanUtils.copyProperties(target, friendRequest);
        return friendRequest;
    }



    @Override
    public List<FriendRequest> revert(List<FriendRequestRequestDto> targets) {
        return targets.stream()
                .map(this::revert)
                .toList();
    }
}
