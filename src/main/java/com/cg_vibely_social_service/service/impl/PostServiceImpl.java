package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.converter.IPostMapper;
import com.cg_vibely_social_service.converter.IUserMapper;
import com.cg_vibely_social_service.entity.Feed.Feed;
import com.cg_vibely_social_service.entity.Feed.FeedItem;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.PostRequestDto;
import com.cg_vibely_social_service.payload.response.LikeResponseDto;
import com.cg_vibely_social_service.payload.response.PostResponseDto;
import com.cg_vibely_social_service.payload.response.UserResponseDto;
import com.cg_vibely_social_service.repository.PostRepository;
import com.cg_vibely_social_service.repository.UserRepository;
import com.cg_vibely_social_service.service.ImageService;
import com.cg_vibely_social_service.service.PostService;
import com.cg_vibely_social_service.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final UserService userService;

    @Override
    public List<PostResponseDto> findByAuthorId(Long authorId) {
        List<Feed> feeds = postRepository.findAllByAuthorId(authorId);
        return feeds.stream()
                .map(source -> this.findById(source.getId()))
                .collect(Collectors.toList());
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
    public PostResponseDto newPost(String source, List<String> files) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        FeedItem feedItem =
                IPostMapper.INSTANCE.newPostConvert(objectMapper.readValue(source, PostRequestDto.class));
        UserImpl user = userService.getCurrentUser();
        feedItem.setAuthorId(user.getId());
        feedItem.setGallery(files);
        feedItem.setCreatedDate(LocalDateTime.now().toString());
        Feed feed = new Feed();
        feed.setFeedItem(feedItem);
        Feed newFeed = postRepository.save(feed);
        return this.findById(newFeed.getId());
    }

    @Override
    public PostResponseDto newPost(String source) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        FeedItem feedItem = IPostMapper.INSTANCE.newPostConvert(objectMapper.readValue(source, PostRequestDto.class));
        UserImpl user = userService.getCurrentUser();
        feedItem.setAuthorId(user.getId());
        feedItem.setCreatedDate(LocalDateTime.now().toString());
        Feed feed = new Feed();
        feed.setFeedItem(feedItem);
        Feed newFeed = postRepository.save(feed);
        return this.findById(newFeed.getId());
    }

    @Override
    public List<PostResponseDto> getNewestPost(int page){
        List<Feed> feeds = postRepository.findLatestFeeds(page);
        return feeds.stream()
                .map(source -> this.findById(source.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public PostResponseDto findById(Long postId) {
        Feed feed = postRepository.findById(postId).orElseThrow();
        FeedItem feedItem = feed.getFeedItem();
        PostResponseDto dto = IPostMapper.INSTANCE.postResponseDto(feedItem);
        dto.setId(feed.getId());
        Optional<User> author = userRepository.findById(feedItem.getAuthorId());
        author.ifPresent(data -> {
            UserResponseDto authorDTO =
                    IUserMapper.INSTANCE.userResponseDTOConvert(data);
            dto.setAuthor(authorDTO);
        });

        if(feedItem.getGallery() != null){
            dto.setGallery(imageService.getImageUrls(feedItem.getGallery()));
        }
        if(feedItem.getLikes() != null ){
            if(feedItem.getLikes().size() != 0) {
                UserImpl user = userService.getCurrentUser();
                for(Long id : feedItem.getLikes()){
                    if(Objects.equals(id, user.getId())){
                        dto.setLiked(true);
                        break;
                    }
                }
                dto.setLikeCount((long) feedItem.getLikes().size());
            }
        }
        if(feedItem.getComments() != null ){
            if(feedItem.getComments().size() != 0) {
                dto.setCommentCount((long) feedItem.getComments().size());
            }
        }
        List<UserResponseDto> newUserTags = new ArrayList<>();
        if(feedItem.getTags() != null) {
            for (Long userid : feedItem.getTags()) {
                Optional<User> user = userRepository.findById(userid);
                user.ifPresent(data -> {
                    UserResponseDto tag =
                            IUserMapper.INSTANCE.userResponseDTOConvert(data);
                    newUserTags.add(tag);
                });
            }
            dto.setUsersTag(newUserTags);
        }
        return dto;
    }
}
