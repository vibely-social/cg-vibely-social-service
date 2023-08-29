package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.entity.Feed.Feed;
import com.cg_vibely_social_service.entity.Feed.FeedItem;
import com.cg_vibely_social_service.payload.response.LikeResponseDto;
import com.cg_vibely_social_service.repository.PostRepository;
import com.cg_vibely_social_service.service.LikeService;
import com.cg_vibely_social_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final PostRepository postRepository;
    private final UserService userService;
    @Override
    public LikeResponseDto likePost(Long postId){
        Feed feed = postRepository.findById(postId).orElseThrow();
        UserImpl user = userService.getCurrentUser();
        FeedItem feedItem = feed.getFeedItem();
        List<Long> likes;
        LikeResponseDto likeResponseDto = new LikeResponseDto();
        if(feedItem.getLikes() != null){
            likes = feedItem.getLikes();
            if(!likes.contains(user.getId())){
                likes.add(user.getId());
                likeResponseDto.setLikeCount((long) likes.size());
                likeResponseDto.setIsLiked(true);
            }
            else{
                likes.removeIf(id -> Objects.equals(id, user.getId()));
                likeResponseDto.setIsLiked(false);
                likeResponseDto.setLikeCount((long) likes.size());
            }
        }
        else{
            likes = new ArrayList<>();
            likes.add(user.getId());
            likeResponseDto = new LikeResponseDto(1L,true);
        }
        feedItem.setLikes(likes);
        feed.setFeedItem(feedItem);
        postRepository.save(feed);
        return likeResponseDto;
    }
}
