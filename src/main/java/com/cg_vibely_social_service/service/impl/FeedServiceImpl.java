package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.converter.IFeedMapper;
import com.cg_vibely_social_service.converter.IUserMapper;
import com.cg_vibely_social_service.entity.Feed.Feed;
import com.cg_vibely_social_service.entity.Feed.FeedItem;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.NewPostRequestDto;
import com.cg_vibely_social_service.payload.response.FeedItemResponseDto;
import com.cg_vibely_social_service.payload.response.FriendResponseDto;
import com.cg_vibely_social_service.payload.response.UserResponseDto;
import com.cg_vibely_social_service.repository.FeedRepository;
import com.cg_vibely_social_service.repository.UserRepository;
import com.cg_vibely_social_service.service.FeedService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    @Override
    public void newPost(String source, List<String> files) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        FeedItem feedItem =
                IFeedMapper.INSTANCE.newPostConvert(objectMapper.readValue(source, NewPostRequestDto.class));
        feedItem.setGallery(files);
        feedItem.setCreatedDate(LocalDateTime.now().toString());
        Feed feed = new Feed();
        feed.setFeedItem(feedItem);
        feedRepository.save(feed);
    }

    @Override
    public void newPost(String source) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        FeedItem feedItem =
                IFeedMapper.INSTANCE.newPostConvert(objectMapper.readValue(source, NewPostRequestDto.class));
        feedItem.setCreatedDate(LocalDateTime.now().toString());
        Feed feed = new Feed();
        feed.setFeedItem(feedItem);
        feedRepository.save(feed);
    }

    @Override
    public List<FeedItemResponseDto> getNewestPost(){
        List<Feed> feeds = feedRepository.findLatestFeeds(0);
        return feeds.stream()
                .map(source -> {
                    FeedItemResponseDto dto = IFeedMapper.INSTANCE.feedResponseDto(source.getFeedItem());
                    dto.setId(source.getId());
                    Optional<User> author = userRepository.findById(source.getFeedItem().getAuthorId());
                    author.ifPresent(data -> {
                        UserResponseDto authorDTO =
                                IUserMapper.INSTANCE.userResponseDTOConvert(data);
                        dto.setAuthor(authorDTO);
                    });
                    List<UserResponseDto> newUserTags = new ArrayList<>();
                    if(source.getFeedItem().getTags() != null) {
                        for (Long id : source.getFeedItem().getTags()) {
                            Optional<User> user = userRepository.findById(id);
                            user.ifPresent(data -> {
                                UserResponseDto tag =
                                        IUserMapper.INSTANCE.userResponseDTOConvert(data);
                                newUserTags.add(tag);
                            });
                        }
                        dto.setUsersTag(newUserTags);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
