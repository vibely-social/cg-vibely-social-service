package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.entity.Feed.Comment;
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

    @Override
    public LikeResponseDto likeComment(Long commentId,Long postId) {
        Feed feed = postRepository.findById(postId).orElseThrow();
        FeedItem feedItem = feed.getFeedItem();
        UserImpl user = userService.getCurrentUser();
        List<Long> likes;
        LikeResponseDto likeResponseDto = new LikeResponseDto();
        if(feedItem.getComments() != null){
            for(Comment comment : feedItem.getComments()){
                if(comment.getCommentId().equals(commentId)){
                    if(comment.getLikes() != null) {
                        likes = comment.getLikes();
                        if(!likes.contains(user.getId())){
                            likes.add(user.getId());
                            likeResponseDto.setIsLiked(true);
                        }
                        else{
                            likes.removeIf(id -> Objects.equals(id, user.getId()));
                            likeResponseDto.setIsLiked(false);
                        }
                        likeResponseDto.setLikeCount((long) likes.size());
                        comment.setLikes(likes);
                    }
                    else{
                        likes = new ArrayList<>();
                        likes.add(user.getId());
                        likeResponseDto.setLikeCount(1L);
                        likeResponseDto.setIsLiked(true);
                        comment.setLikes(likes);
                    }
                    break;
                }
            }
        }
        feed.setFeedItem(feedItem);
        postRepository.save(feed);
        return likeResponseDto;
    }

    @Override
    public LikeResponseDto likeReply(Long replyId, Long commentId, Long postId) {
return null;
    }
}
