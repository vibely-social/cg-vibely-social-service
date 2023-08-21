package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.converter.IFeedMapper;
import com.cg_vibely_social_service.converter.IUserMapper;
import com.cg_vibely_social_service.entity.Feed.Feed;
import com.cg_vibely_social_service.entity.Feed.FeedItem;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.PostRequestDto;
import com.cg_vibely_social_service.payload.response.FeedItemResponseDto;
import com.cg_vibely_social_service.payload.response.PostResponseDto;
import com.cg_vibely_social_service.payload.response.UserResponseDto;
import com.cg_vibely_social_service.repository.PostRepository;
import com.cg_vibely_social_service.repository.UserRepository;
import com.cg_vibely_social_service.service.ImageService;
import com.cg_vibely_social_service.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;


    @Override
    public List<PostResponseDto> findByUser(User user) {
        return null;
    }

    @Override
    public List<PostResponseDto> findAll() {
        return null;
    }

    @Override
    public void save(PostRequestDto postRequestDto) {

    }

    @Override
    public void deleteById(Long postId) {

    }

    @Override
    public PostResponseDto update(PostRequestDto postRequestDto) {
        return null;
    }

    @Override
    public void newPost(String source, List<String> files) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        FeedItem feedItem =
                IFeedMapper.INSTANCE.newPostConvert(objectMapper.readValue(source, PostRequestDto.class));
        feedItem.setGallery(files);
        feedItem.setCreatedDate(LocalDateTime.now().toString());
        Feed feed = new Feed();
        feed.setFeedItem(feedItem);
        postRepository.save(feed);
    }

    @Override
    public void newPost(String source) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        FeedItem feedItem =
                IFeedMapper.INSTANCE.newPostConvert(objectMapper.readValue(source, PostRequestDto.class));
        feedItem.setCreatedDate(LocalDateTime.now().toString());
        Feed feed = new Feed();
        feed.setFeedItem(feedItem);
        postRepository.save(feed);
    }

    @Override
    public List<FeedItemResponseDto> getNewestPost(int page){
        List<Feed> feeds = postRepository.findLatestFeeds(page);
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

                    if(!source.getFeedItem().getGallery().isEmpty()){
                        dto.setGallery(imageService.getImageUrls(source.getFeedItem().getGallery()));
                    }
                    if(source.getFeedItem().getLikes() != null && source.getFeedItem().getLikes().size() == 0){
                        dto.setLikeCount((long) source.getFeedItem().getLikes().size());
                    }
                    if(source.getFeedItem().getComments() != null && source.getFeedItem().getComments().size() == 0){
                        dto.setCommentCount((long) source.getFeedItem().getComments().size());
                    }
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
