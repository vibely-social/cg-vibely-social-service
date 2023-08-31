package com.cg_vibely_social_service.service;

import com.cg_vibely_social_service.entity.Feed.Comment;
import com.cg_vibely_social_service.entity.Feed.FeedItem;
import com.cg_vibely_social_service.payload.request.CommentRequestDto;
import com.cg_vibely_social_service.payload.response.CommentResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CommentService {

    CommentResponseDto newComment(Long postId, String content, MultipartFile source) throws IOException;

    List<CommentResponseDto> getComments(Long postId);

    CommentResponseDto replyComment(Long postId, Long commentId , String content, MultipartFile source) throws IOException;

    CommentResponseDto getTopComment(FeedItem feedItem);
    FeedItem saveComment(FeedItem feedItem , Comment comment);
    CommentResponseDto editComment(Long postId,CommentRequestDto commentRequestDto,MultipartFile file) throws IOException;
}
