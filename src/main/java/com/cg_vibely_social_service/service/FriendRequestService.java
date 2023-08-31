package com.cg_vibely_social_service.service;

import com.cg_vibely_social_service.payload.request.FriendRequestRequestDto;
import com.cg_vibely_social_service.payload.request.ResolveRequestDto;
import com.cg_vibely_social_service.payload.response.FriendRequestResponseDto;

import java.util.List;

public interface FriendRequestService {
    void saveFriendRequest(Long friendId);

    List<FriendRequestResponseDto> findAllFriendRequestByUserId(Long userId);
    List<FriendRequestResponseDto> findAllFriendRequestByFriendId(Long friendId);

//    List<FriendRequestDto> findAllFriendRequestByFriendId(Long friendId);

    void acceptFriendRequest(Long friendId);
    void removeFriend(Long friendId);

//    void resolveRequest(ResolveRequestDto resolveRequestDto);
}
