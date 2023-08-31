package com.cg_vibely_social_service.service;

import com.cg_vibely_social_service.payload.request.FriendRequestRequestDto;
import com.cg_vibely_social_service.payload.request.ResolveRequestDto;
import com.cg_vibely_social_service.payload.response.FriendRequestResponseDto;

import java.util.List;

public interface FriendRequestService {
    void saveFriendRequest(FriendRequestRequestDto friendRequestRequestDto);

    List<FriendRequestResponseDto> findAllFriendRequestByUserId(Long userId);

    List<FriendRequestResponseDto> findAllFriendRequestByFriendId(Long friendId);

    void resolveRequest(ResolveRequestDto resolveRequestDto);
}
