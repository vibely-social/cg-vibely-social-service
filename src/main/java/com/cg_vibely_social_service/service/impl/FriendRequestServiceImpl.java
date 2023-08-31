package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.entity.Friend;
import com.cg_vibely_social_service.entity.FriendRequest;
import com.cg_vibely_social_service.payload.request.FriendRequestRequestDto;
import com.cg_vibely_social_service.payload.request.ResolveRequestDto;
import com.cg_vibely_social_service.payload.response.FriendRequestResponseDto;
import com.cg_vibely_social_service.repository.FriendRepository;
import com.cg_vibely_social_service.repository.FriendRequestRepository;
import com.cg_vibely_social_service.service.FriendRequestService;
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
    public void saveFriendRequest(FriendRequestRequestDto friendRequestRequestDto) {
        FriendRequest friendRequest = FriendRequest.builder()
                .userId(friendRequestRequestDto.getUserId())
                .friendId(friendRequestRequestDto.getFriendId())
                .build();
        friendRequestRepository.save(friendRequest);
    }

    @Override
    public List<FriendRequestResponseDto> findAllFriendRequestByUserId(Long userId) {
        List<FriendRequest> friendRequestDtoList = friendRequestRepository.findFriendRequestsByUserId(userId);
        return getFriendRequestDtos(friendRequestDtoList);
    }

    @Override
    public List<FriendRequestResponseDto> findAllFriendRequestByFriendId(Long friendId) {
        List<FriendRequest> friendRequestDtoList = friendRequestRepository.findFriendRequestsByFriendId(friendId);
        return getFriendRequestDtos(friendRequestDtoList);
    }

    @Override
    public void resolveRequest(ResolveRequestDto resolveRequestDto) {
        FriendRequest friendRequest = friendRequestRepository.findById(resolveRequestDto.getFriendRequestId()).orElseThrow();
        if(Objects.equals(resolveRequestDto.getStatus() ,"confirm")){
            Friend friend1 = Friend.builder()
                    .userId(friendRequest.getUserId())
                    .friendId(friendRequest.getFriendId())
                    .build();
            Friend friend2 = Friend.builder()
                    .userId(friendRequest.getFriendId())
                    .friendId(friendRequest.getUserId())
                    .build();
            friendRepository.save(friend1);
            friendRepository.save(friend2);
        }
        friendRequestRepository.deleteById(resolveRequestDto.getFriendRequestId());
    }

    private List<FriendRequestResponseDto> getFriendRequestDtos(List<FriendRequest> friendRequestDtoList) {
        List<FriendRequestResponseDto> friendRequestResponseDtos = new ArrayList<>();
        for (FriendRequest friendRequest : friendRequestDtoList) {
            FriendRequestResponseDto friendRequestResponseDto = FriendRequestResponseDto.builder()
                    .userId(friendRequest.getUserId())
                    .friendId((friendRequest.getFriendId()))
                    .build();
            friendRequestResponseDtos.add(friendRequestResponseDto);
        }
        return friendRequestResponseDtos;
    }
}
