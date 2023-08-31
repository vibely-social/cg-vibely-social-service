package com.cg_vibely_social_service.service;

import com.cg_vibely_social_service.payload.request.FriendRequestDto;
import com.cg_vibely_social_service.payload.request.ResolveRequestDto;

import java.util.List;

public interface FriendRequestService {
    void saveFriendRequest(FriendRequestDto friendRequestDto);

    List<FriendRequestDto> findAllFriendRequestByUserId(Long userId);

    List<FriendRequestDto> findAllFriendRequestByFriendId(Long friendId);

    void resolveRequest(ResolveRequestDto resolveRequestDto);
}
