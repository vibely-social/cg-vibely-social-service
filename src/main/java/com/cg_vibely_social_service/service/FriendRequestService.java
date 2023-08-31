package com.cg_vibely_social_service.service;
import com.cg_vibely_social_service.payload.request.FriendRequestDto;

import java.util.List;

public interface FriendRequestService {
    void saveFriendRequest(Long friendId);

    List<FriendRequestDto> findAllFriendRequestByUserId(Long userId);

    List<FriendRequestDto> findAllFriendRequestByFriendId(Long friendId);

    void acceptFriendRequest(Long friendId);
    void removeFriend(Long friendId);
}
