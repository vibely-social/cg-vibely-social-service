package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.converter.impl.FriendRequestDtoConverter;
import com.cg_vibely_social_service.entity.Friend;
import com.cg_vibely_social_service.entity.FriendRequest;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.FriendRequestDto;
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
        private final UserService userService;
        private  final FriendRequestDtoConverter friendRequestDtoConverter;
        private final ImageServiceImpl imageService;

        private final FriendRepository friendRepository;

    @Override
    public void saveFriendRequest(Long friendId) {
        UserImpl currentUser = userService.getCurrentUser();
        User user = userService.findById(currentUser.getId());
        User friend = userService.findById(friendId);

        FriendRequest friendRequest = FriendRequest.builder()
                .user(user)
                .friend(friend)
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
                .user(user)
                .friend(friend)
                .build();
        friendRequestRepository.delete(friendRequest);
    }


    private List<FriendRequestDto> getFriendRequestDtos(List<FriendRequest> friendRequestDtoList) {
        List<FriendRequestDto> friendRequestDtos = new ArrayList<>();
        for (FriendRequest friendRequest : friendRequestDtoList) {
            FriendRequestDto friendRequestDto = FriendRequestDto.builder()
                    .id(friendRequest.getId())
                    .userId((friendRequest.getUser()).getId())
                    .friendId((friendRequest.getFriend()).getId())
                    .name((friendRequest.getUser()).getFirstName() + friendRequest.getUser().getLastName())
                    .avatarUrl(imageService.getImageUrl(friendRequest.getUser().getAvatar()))
                    .build();
            friendRequestDtos.add(friendRequestDto);
        }
        return friendRequestDtos;
    }
}
