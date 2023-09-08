package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.converter.IPostMapper;
import com.cg_vibely_social_service.converter.IUserMapper;
import com.cg_vibely_social_service.entity.Feed.Feed;
import com.cg_vibely_social_service.entity.Feed.FeedItem;
import com.cg_vibely_social_service.entity.Media;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.PostRequestDto;
import com.cg_vibely_social_service.payload.response.CommentResponseDto;
import com.cg_vibely_social_service.payload.response.LikeResponseDto;
import com.cg_vibely_social_service.payload.response.PostResponseDto;
import com.cg_vibely_social_service.payload.response.UserResponseDto;
import com.cg_vibely_social_service.repository.MediaRepository;
import com.cg_vibely_social_service.repository.PostRepository;
import com.cg_vibely_social_service.repository.UserRepository;
import com.cg_vibely_social_service.service.CommentService;
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
    private final CommentService commentService;
    private final MediaRepository mediaRepository;

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
        List<Long> subscribers = new ArrayList<>();
        subscribers.add(user.getId());
        feedItem.setSubscribers(subscribers);
        feed.setFeedItem(feedItem);
        Feed newFeed = postRepository.save(feed);

        //Save to Media Table
        for (String file : files) {
            Media media = Media.builder()
                    .userId(user.getId())
                    .postID(newFeed.getId())
                    .fileName(file)
                    .createdAt(LocalDateTime.now())
                    .build();
            mediaRepository.save(media);
        }

        return this.findById(newFeed.getId());
    }

    @Override
    public PostResponseDto newPost(String source) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        FeedItem feedItem = IPostMapper.INSTANCE.newPostConvert(objectMapper.readValue(source, PostRequestDto.class));
        UserImpl user = userService.getCurrentUser();
        feedItem.setAuthorId(user.getId());
        feedItem.setCreatedDate(LocalDateTime.now().toString());
        List<Long> subscribers = new ArrayList<>();
        subscribers.add(user.getId());
        feedItem.setSubscribers(subscribers);
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
        UserImpl user1 = userService.getCurrentUser();
        PostResponseDto dto = IPostMapper.INSTANCE.convertToDTO(feedItem,user1.getId());
        dto.setId(feed.getId());
        Optional<User> author = userRepository.findById(feedItem.getAuthorId());
        author.ifPresent(data -> {
            UserResponseDto authorDTO =
                    IUserMapper.INSTANCE.userResponseDTOConvert(data);

            if (author.get().getAvatar() == null && author.get().getGoogleAvatar() != null) {
                authorDTO.setAvatar(author.get().getGoogleAvatar());
            } else {
                authorDTO.setAvatar(imageService.getImageUrl(author.get().getAvatar()));
            }

            dto.setAuthor(authorDTO);
        });

        if(feedItem.getGallery() != null){
            dto.setGallery(imageService.getImageUrls(feedItem.getGallery()));
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
        CommentResponseDto topComment = commentService.getTopComment(feedItem);

        if(Objects.nonNull(topComment)){
            User user = userService.findById(topComment.getAuthor().getId());
            topComment.setAuthor(IUserMapper.INSTANCE.userResponseDTOConvert(user));
            dto.setTopComment(topComment);
        }
        return dto;
    }

    @Override
    public List<PostResponseDto> getPostPagingByAuthor(Long authorId, int page) {
        List<Feed> feeds = postRepository.findPagingFeedByAuthorId(authorId, 5 * (page - 1));
        return feeds.stream()
                .map(source -> this.findById(source.getId()))
                .collect(Collectors.toList());
    }
}
