package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.entity.Friend;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.response.FriendResponseDto;
import com.cg_vibely_social_service.repository.FriendRepository;
import com.cg_vibely_social_service.repository.UserRepository;
import com.cg_vibely_social_service.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final Converter<FriendResponseDto, User> converter;

    @Override
    public List<FriendResponseDto> findFriendsByUserId(Long userId) {
        List<Friend> friends1 = friendRepository.findAllByUserId(userId);
        List<Friend> friends2 = friendRepository.findAllByFriendId(userId);
        List<Long> friendsId1 = new ArrayList<>(friends1.stream().map(Friend::getFriendId).toList());
        List<Long> friendsId2 = friends2.stream().map(Friend::getUserId).toList();
        friendsId1.addAll(friendsId2);
        List<User> friends = userRepository.findAllById(friendsId1);
        return friends.stream().map(converter::revert).collect(Collectors.toList());
    }

}
