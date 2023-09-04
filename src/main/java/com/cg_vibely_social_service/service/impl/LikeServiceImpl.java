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
        if(Objects.nonNull(feedItem.getLikes())){
            likes = feedItem.getLikes();
            if(!likes.contains(user.getId())){
                likes.add(user.getId());
            }
            else{
                likes.remove(user.getId());
            }
        }
        else{
            likes = new ArrayList<>();
            likes.add(user.getId());
        }
        likeResponseDto.setLikeCount((long) likes.size());
        likeResponseDto.setIsLiked(likes.contains(user.getId()));
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
                        }
                        else{
                            likes.remove(user.getId());
                        }
                    }
                    else{
                        likes = new ArrayList<>();
                        likes.add(user.getId());
                    }
                    likeResponseDto.setLikeCount((long) likes.size());
                    likeResponseDto.setIsLiked(likes.contains(user.getId()));
                    comment.setLikes(likes);
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
        Feed feed = postRepository.findById(postId).orElseThrow();
        FeedItem feedItem = feed.getFeedItem();
        UserImpl user = userService.getCurrentUser();
        List<Long> likes;
        LikeResponseDto likeResponseDto = new LikeResponseDto();
        Comment comment = Objects.requireNonNull(feedItem.getComments())
                .stream()
                .filter(cmt -> Objects.equals(cmt.getCommentId(), commentId))
                .findFirst()
                .orElseThrow();
        Comment reply =
                Objects.requireNonNull(comment).getReplyComments()
                        .stream()
                        .filter(rep -> Objects.equals(rep.getCommentId(), replyId))
                        .findFirst().orElseThrow();
        if(reply.getLikes() != null){
            likes = reply.getLikes();
            if(reply.getLikes().contains(user.getId())){
                likes.remove(user.getId());
            }
            else{
                likes.add(user.getId());
            }
        }
        else{
            likes = new ArrayList<>();
            likes.add(user.getId());
        }
        reply.setLikes(likes);
        likeResponseDto.setLikeCount((long) likes.size());
        likeResponseDto.setIsLiked(likes.contains(user.getId()));
        feed.setFeedItem(feedItem);
        postRepository.save(feed);
        return likeResponseDto;
    }
}
