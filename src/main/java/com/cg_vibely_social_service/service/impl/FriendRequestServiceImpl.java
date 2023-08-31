package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.converter.impl.FriendRequestDtoConverter;
import com.cg_vibely_social_service.entity.FriendRequest;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.FriendRequestDto;
import com.cg_vibely_social_service.repository.FriendRequestRepository;
import com.cg_vibely_social_service.service.FriendRequestService;
import com.cg_vibely_social_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {
        private final FriendRequestRepository friendRequestRepository;
        private final UserService userService;
        private  final FriendRequestDtoConverter friendRequestDtoConverter;

    @Override
    public void saveFriendRequest(FriendRequestDto friendRequestDto) {
        User user1 = userService.findById(friendRequestDto.getUserId());
        User user2 = userService.findById(friendRequestDto.getFriendId());
        FriendRequest friendRequest = FriendRequest.builder()
                .user(user1)
                .friend(user2)
                .build();
        friendRequestRepository.save(friendRequest);
    }

    @Override
    public List<FriendRequestDto> findAllFriendRequestByUserId(Long userId) {
        List<FriendRequest> friendRequestDtoList = friendRequestRepository.findFriendRequestsByUser_id(userId);
        return getFriendRequestDtos(friendRequestDtoList);
    }

    @Override
    public List<FriendRequestDto> findAllFriendRequestByFriendId(Long friendId) {
        List<FriendRequest> friendRequestDtoList = friendRequestRepository.findFriendRequestsByFriend_Id(friendId);
        return getFriendRequestDtos(friendRequestDtoList);
    }

    private List<FriendRequestDto> getFriendRequestDtos(List<FriendRequest> friendRequestDtoList) {
        List<FriendRequestDto> friendRequestDtos = new ArrayList<>();
        for (FriendRequest friendRequest : friendRequestDtoList) {
            FriendRequestDto friendRequestDto = FriendRequestDto.builder()
                    .id(friendRequest.getId())
                    .userId((friendRequest.getUser()).getId())
                    .friendId((friendRequest.getFriend()).getId())
                    .name((friendRequest.getFriend()).getFirstName() + friendRequest.getFriend().getLastName())
                    .avatarUrl("default")
                    .build();
            friendRequestDtos.add(friendRequestDto);
        }
        return friendRequestDtos;
    }
}
