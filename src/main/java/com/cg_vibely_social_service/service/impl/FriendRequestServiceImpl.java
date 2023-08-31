package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.converter.impl.FriendRequestDtoConverter;
import com.cg_vibely_social_service.entity.Friend;
import com.cg_vibely_social_service.entity.FriendRequest;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.response.FriendRequestResponseDto;
import com.cg_vibely_social_service.repository.FriendRepository;
import com.cg_vibely_social_service.repository.FriendRequestRepository;
import com.cg_vibely_social_service.service.FriendRequestService;
import com.cg_vibely_social_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendRequestServiceImpl implements FriendRequestService {
        private final FriendRequestRepository friendRequestRepository;
        private final FriendRepository friendRepository;
        private final UserService userService;
        private  final FriendRequestDtoConverter friendRequestDtoConverter;
        private final ImageServiceImpl imageService;


    @Override
    public void saveFriendRequest(Long friendId) {
        UserImpl currentUser = userService.getCurrentUser();
        User user = userService.findById(currentUser.getId());
        User friend = userService.findById(friendId);

        FriendRequest friendRequest = FriendRequest.builder()
//                .user(user)
//                .friend(friend)
                .build();
        friendRequestRepository.save(friendRequest);
    }

    @Override
    public List<FriendRequestResponseDto> findAllFriendRequestByUserId(Long userId) {
        List<FriendRequest> friendRequestDtoList = friendRequestRepository.findAllByUserId(userId);
        return getFriendRequestDtos(friendRequestDtoList);
    }

    @Override
    public List<FriendRequestResponseDto> findAllFriendRequestByFriendId(Long friendId) {
        List<FriendRequest> friendRequestDtoList = friendRequestRepository.findAllByFriendId(friendId);
        return getFriendRequestDtos(friendRequestDtoList);
    }

    @Override
    public void acceptFriendRequest(Long friendId) {
        UserImpl currentUser = userService.getCurrentUser();
        User user = userService.findById(currentUser.getId());
        User friend = userService.findById(friendId);
        friendRepository.save(new Friend(user.getId(), friend.getId()));
        friendRequestRepository.deleteAllByUserAndFriendOrUserAndFriend(user,friend,friend,user);
    }

    @Override
    public void removeFriend(Long friendId) {
        UserImpl currentUser = userService.getCurrentUser();
        User user = userService.findById(currentUser.getId());
        User friend = userService.findById(friendId);
        FriendRequest friendRequest = FriendRequest.builder()
//                .user(user)
//                .friend(friend)
                .build();
        friendRequestRepository.delete(friendRequest);
    }


    private List<FriendRequestResponseDto> getFriendRequestDtos(List<FriendRequest> friendRequestDtoList) {
        List<FriendRequestResponseDto> friendRequestDtos = new ArrayList<>();
        for (FriendRequest friendRequest : friendRequestDtoList) {
            FriendRequestResponseDto friendRequestDto = FriendRequestResponseDto.builder().build();
//                    .id(friendRequest.getId())
//                    .userId((friendRequest.getUser()).getId())
//                    .friendId((friendRequest.getFriend()).getId())
//                    .name((friendRequest.getUser()).getFirstName() + friendRequest.getUser().getLastName())
//                    .avatarUrl(imageService.getImageUrl(friendRequest.getUser().getAvatar()))
//                    .build();
            friendRequestDtos.add(friendRequestDto);
        }
        return friendRequestDtos;
    }
}
