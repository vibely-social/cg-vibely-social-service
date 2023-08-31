package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.entity.Friend;
import com.cg_vibely_social_service.entity.FriendRequest;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.FriendRequestDto;
import com.cg_vibely_social_service.payload.request.ResolveRequestDto;
import com.cg_vibely_social_service.repository.FriendRepository;
import com.cg_vibely_social_service.repository.FriendRequestRepository;
import com.cg_vibely_social_service.service.FriendRequestService;
import com.cg_vibely_social_service.service.FriendService;
import com.cg_vibely_social_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {
        private final FriendRequestRepository friendRequestRepository;
        private final FriendRepository friendRepository;
        private final UserService userService;
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

    @Override
    public void resolveRequest(ResolveRequestDto resolveRequestDto) {
        if(Objects.equals(resolveRequestDto.getStatus() ,"confirm")){
            Friend friend1 = Friend.builder()
                    .userId(resolveRequestDto.getUserId())
                    .friendId(resolveRequestDto.getFriendId())
                    .build();
            Friend friend2 = Friend.builder()
                    .userId(resolveRequestDto.getFriendId())
                    .friendId(resolveRequestDto.getUserId())
                    .build();
            friendRepository.save(friend1);
            friendRepository.save(friend2);
        }
        friendRequestRepository.deleteById(resolveRequestDto.getId());
    }

    private List<FriendRequestDto> getFriendRequestDtos(List<FriendRequest> friendRequestDtoList) {
        List<FriendRequestDto> friendRequestDtos = new ArrayList<>();
        for (FriendRequest friendRequest : friendRequestDtoList) {
            FriendRequestDto friendRequestDto = FriendRequestDto.builder()
                    .id(friendRequest.getId())
                    .userId((friendRequest.getUser()).getId())
                    .friendId((friendRequest.getFriend()).getId())
                    .name((friendRequest.getFriend()).getFirstName() + friendRequest.getFriend().getLastName())
                    .avatarUrl("default") // There is currently no image data available
                    .build();
            friendRequestDtos.add(friendRequestDto);
        }
        return friendRequestDtos;
    }
}
