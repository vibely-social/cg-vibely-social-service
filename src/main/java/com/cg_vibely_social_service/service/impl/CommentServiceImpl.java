package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.converter.ICommentMapper;
import com.cg_vibely_social_service.converter.IPostMapper;
import com.cg_vibely_social_service.converter.IUserMapper;
import com.cg_vibely_social_service.entity.Feed.Comment;
import com.cg_vibely_social_service.entity.Feed.Feed;
import com.cg_vibely_social_service.entity.Feed.FeedItem;
import com.cg_vibely_social_service.entity.Friend;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.CommentRequestDto;
import com.cg_vibely_social_service.payload.response.CommentResponseDto;
import com.cg_vibely_social_service.payload.response.UserResponseDto;
import com.cg_vibely_social_service.repository.FriendRepository;
import com.cg_vibely_social_service.repository.PostRepository;
import com.cg_vibely_social_service.service.CommentService;
import com.cg_vibely_social_service.service.FriendService;
import com.cg_vibely_social_service.service.ImageService;
import com.cg_vibely_social_service.service.UserService;
import com.google.api.gax.rpc.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.NotAcceptableStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final FriendService friendService;
    @Override
    public CommentResponseDto newComment(Long postId, String content, MultipartFile source) throws IOException {
            Feed feed = postRepository.findById(postId).orElseThrow();
            UserImpl user = userService.getCurrentUser();
            FeedItem feedItem = feed.getFeedItem();
            Comment comment = Comment.builder()
                    .date(LocalDateTime.now().toString())
                    .content(content)
                    .userId(user.getId())
                    .replyComments(new ArrayList<>())
                    .likes(new ArrayList<>())
                    .build();
            if(source != null)  {
                String fileName = imageService.save(source);
                comment.setGallery(fileName);
            }
            FeedItem newFeedItem = this.saveComment(feedItem,comment);
            feed.setFeedItem(newFeedItem);

            postRepository.save(feed);
            CommentResponseDto commentResponseDto =
                         ICommentMapper.INSTANCE.commentResponseDto(comment, user.getId());
            User user1 = userService.findById(user.getId());
            UserResponseDto userResponseDto = IUserMapper.INSTANCE.userResponseDTOConvert(user1);
            commentResponseDto.setAuthor(userResponseDto);
            return commentResponseDto;
    }

    @Override
    public List<CommentResponseDto> getComments(Long postId) {
        Feed feed = postRepository.findById(postId).orElseThrow();
        FeedItem feedItem = feed.getFeedItem();
        UserImpl user1 = userService.getCurrentUser();
        List <CommentResponseDto> commentResponseDTOs = new ArrayList<>();
        if(Objects.nonNull(feedItem.getComments())) {
            for(Comment comment : feedItem.getComments()) {
                List<CommentResponseDto> replyComments = new ArrayList<>();
                if(Objects.nonNull(comment.getReplyComments())) {
                    for (Comment reply : comment.getReplyComments()){
                        CommentResponseDto replyDto =
                                ICommentMapper.INSTANCE.commentResponseDto(reply, user1.getId());
                        User user = userService.findById(reply.getUserId());
                        UserResponseDto userResponseDto =
                                IUserMapper.INSTANCE.userResponseDTOConvert(user);
                        replyDto.setAuthor(userResponseDto);
                        if(reply.getLikes() != null){
                            replyDto.setLikeCount((long) reply.getLikes().size());
                            replyDto.setLiked(reply.getLikes().contains(user.getId()));
                        }
                        replyComments.add(replyDto);
                    }
                }
                CommentResponseDto commentResponseDto =
                        ICommentMapper.INSTANCE.commentResponseDto(comment,user1.getId());
                User user = userService.findById(comment.getUserId());
                UserResponseDto userResponseDto =
                        IUserMapper.INSTANCE.userResponseDTOConvert(user);
                commentResponseDto.setAuthor(userResponseDto);
                commentResponseDto.setReplyCommentDTOs(replyComments);
                if(Objects.nonNull(comment.getLikes())){
                    commentResponseDto.setLiked(comment.getLikes().contains(user.getId()));
                    commentResponseDto.setLikeCount((long) comment.getLikes().size());
                }
                commentResponseDTOs.add(commentResponseDto);
            }
            return commentResponseDTOs;
        }
        return null;
    }

    @Override
    public CommentResponseDto replyComment(Long postId, Long commentId, String content, MultipartFile source) throws IOException {
        Feed feed = postRepository.findById(postId).orElseThrow();
        FeedItem feedItem = feed.getFeedItem();
        UserImpl user = userService.getCurrentUser();
        Comment comment = Comment.builder()
                        .date(LocalDateTime.now().toString())
                        .content(content)
                        .userId(user.getId())
                        .likes(new ArrayList<>())
                        .build();

        if(source != null)  {
            String fileName = imageService.save(source);
            comment.setGallery(fileName);
        }
        List<Comment> reply;
        Comment cmt =  Objects.requireNonNull(feedItem.getComments())
                            .stream()
                            .filter(com -> Objects.equals(com.getCommentId(), commentId))
                            .findFirst().orElseThrow();
        if(Objects.nonNull(cmt.getReplyComments())) {
            reply = cmt.getReplyComments();
            comment.setCommentId((long) (reply.size() + 1));
        }
        else{
            comment.setCommentId(1L);
            reply = new ArrayList<>();
        }
        reply.add(comment);
        cmt.setReplyComments(reply);
        feed.setFeedItem(feedItem);
        postRepository.save(feed);
        CommentResponseDto commentResponseDto =
                    ICommentMapper.INSTANCE.commentResponseDto(comment, user.getId());
        User user1 = userService.findById(user.getId());
        UserResponseDto userResponseDto = IUserMapper.INSTANCE.userResponseDTOConvert(user1);
        commentResponseDto.setAuthor(userResponseDto);
        return commentResponseDto;
    }

    @Override
    public CommentResponseDto getTopComment(FeedItem feedItem) {
        Comment topComment = null;
        UserImpl user = userService.getCurrentUser();
        List <Long> friends = friendService.findAllFriends(user.getId());

        if(feedItem.getComments() != null){
            List<Comment> comments = feedItem.getComments();
            if(!friends.isEmpty()) {
                comments.sort((comment1, comment2) ->
                        Math.toIntExact(comment2.getCommentId() - comment1.getCommentId()));
                for(Comment comment : comments){
                    if(friends.contains(comment.getCommentId())){
                        return ICommentMapper.INSTANCE.commentResponseDto(comment, user.getId());
                    }
                }
            }
            for(Comment comment : comments){
                long max = 0;
                if(comment.getLikes() != null){
                    if(comment.getLikes().size() > max) {
                        topComment = comment;
                    }
                }
            }
            return ICommentMapper.INSTANCE.commentResponseDto(topComment, user.getId());
        }
        return null;
    }

    @Override
    public FeedItem saveComment(FeedItem feedItem, Comment commentRequest) {
        List<Comment> commentList = feedItem.getComments();
        if(commentRequest.getCommentId() != null){
            Comment oldComment = commentList.stream()
                    .filter(comment -> Objects.equals(comment.getCommentId(), commentRequest.getCommentId()))
                    .findFirst()
                    .orElse(null);
            if(oldComment != null){
                oldComment.setContent(commentRequest.getContent());
                oldComment.setGallery(commentRequest.getGallery());
                commentList.set(commentList.indexOf(oldComment), oldComment);
            }
            feedItem.setComments(commentList);
            return feedItem;
        }
        commentRequest.setCommentId(feedItem.getComments() != null ? commentList.size() + 1 : 1L);
        if(!Objects.nonNull(commentList)){
            commentList = new ArrayList<>();
        }
        commentList.add(commentRequest);
        feedItem.setComments(commentList);
        return feedItem;
    }

    @Override
    public CommentResponseDto editComment(Long postId,CommentRequestDto commentRequestDto,MultipartFile file) throws IOException {
        Feed feed = postRepository.findById(postId).orElseThrow();
        UserImpl user = userService.getCurrentUser();
        if(!Objects.equals(commentRequestDto.getAuthorId(), user.getId())){
            throw new NotAcceptableStatusException("Invalid request");
        }
        String source = null;
        if(file != null) {
            source = imageService.save(file);
        }
        FeedItem feedItem = feed.getFeedItem();
        Comment comment = Comment.builder()
                            .userId(user.getId())
                            .commentId(commentRequestDto.getCommentId())
                            .content(commentRequestDto.getContent())
                            .gallery(source)
                            .build();
        FeedItem newFeedItem = this.saveComment(feedItem,comment);
        feed.setFeedItem(newFeedItem);
        postRepository.save(feed);
        return CommentResponseDto.builder()
                .content(commentRequestDto.getContent())
                .gallery(source)
                .build();
    }
}
