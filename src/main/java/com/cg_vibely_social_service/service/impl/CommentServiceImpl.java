package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.entity.Feed.Comment;
import com.cg_vibely_social_service.entity.Feed.Feed;
import com.cg_vibely_social_service.entity.Feed.FeedItem;
import com.cg_vibely_social_service.repository.PostRepository;
import com.cg_vibely_social_service.service.CommentService;
import com.cg_vibely_social_service.service.ImageService;
import com.cg_vibely_social_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final ImageService imageService;
    @Override
    public void newComment(Long postId,String content, MultipartFile source) throws IOException {
            Feed feed = postRepository.findById(postId).orElseThrow();
            Comment comment = new Comment();
            comment.setDate(LocalDateTime.now().toString());
            comment.setContent(content);
            UserImpl user = userService.getCurrentUser();
            comment.setUserId(user.getId());
            if(source != null)  {
                String fileName = imageService.save(source);
                comment.setGallery(fileName);
            }
            FeedItem feedItem = feed.getFeedItem();
            List<Comment> commentList;
            if(feedItem.getComments() != null){
                commentList = feedItem.getComments();
                comment.setCommentId((long) (commentList.size() + 1));
            }
            else{
                commentList = new ArrayList<>();
                comment.setCommentId(1L);
            }
            commentList.add(comment);
            feedItem.setComments(commentList);
            feed.setFeedItem(feedItem);
            postRepository.save(feed);
    }
}
