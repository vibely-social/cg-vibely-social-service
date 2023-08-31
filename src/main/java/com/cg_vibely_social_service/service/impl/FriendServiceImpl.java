package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.entity.Friend;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.response.FriendResponseDto;
import com.cg_vibely_social_service.repository.FriendRepository;
import com.cg_vibely_social_service.repository.UserRepository;
import com.cg_vibely_social_service.service.FriendService;
import com.cg_vibely_social_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final Converter<FriendResponseDto, User> converter;

    @Override
    public List<FriendResponseDto> findFriendsByUserId(Long userId) {
        List<Friend> friendList = friendRepository.findAllByUserIdOrFriendId(userId, userId);
        List<Long> friendsId = friendList.stream().map(friend -> {
            if (userId.equals(friend.getUserId())) {
                return friend.getFriendId();
            } else {
                return friend.getUserId();
            }
        }).collect(Collectors.toList());
        List<User> friends = userRepository.findAllById(friendsId);
        return friends.stream().map(converter::revert).collect(Collectors.toList());
    }

    @Override
    public void removeFriend(Long friendId) {
        Long userId = userService.getCurrentUser().getId();
        friendRepository.deleteAllByUserIdAndFriendIdOrUserIdAndFriendId(userId, friendId, friendId, userId);
    }

}
