package com.cg_vibely_social_service.converter;

import com.cg_vibely_social_service.entity.Feed.Comment;
import com.cg_vibely_social_service.entity.Feed.FeedItem;
import com.cg_vibely_social_service.payload.request.CommentRequestDto;
import com.cg_vibely_social_service.payload.request.PostRequestDto;
import com.cg_vibely_social_service.payload.response.CommentResponseDto;
import com.cg_vibely_social_service.payload.response.PostResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IPostMapper {
    IPostMapper INSTANCE = Mappers.getMapper(IPostMapper.class);

    @Mapping(target = "authorId", source="postRequestDto.authorId")
    @Mapping(target = "content", source="postRequestDto.content")
    @Mapping(target = "privacy", source="postRequestDto.privacy")
    @Mapping(target = "tags", source="postRequestDto.tags")
    FeedItem newPostConvert(PostRequestDto postRequestDto);

    @Mapping(target = "author.id", source="feedItem.authorId")
    @Mapping(target = "content", source="feedItem.content")
    @Mapping(target = "privacy", source="feedItem.privacy")
    @Mapping(target = "createdDate", source="feedItem.createdDate")
    PostResponseDto postResponseDto(FeedItem feedItem);

    @Mapping(target = "commentId", source="comment.commentId")
    @Mapping(target = "content", source="comment.content")
    @Mapping(target = "author.id", source="comment.userId")
    @Mapping(target = "gallery", source="comment.gallery")
    @Mapping(target = "date", source="comment.date")
    CommentResponseDto commentResponseDto(Comment comment);
    void convertRequestToCommentEntity(CommentRequestDto commentRequestDto, @MappingTarget Comment comment);

    @Mapping(target = "commentId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "content", source="newComment.content")
    @Mapping(target = "gallery", source="newComment.gallery")
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "replyComments", ignore = true)
    Comment editComment(Comment newComment);
    void editComment(Comment newComment, @MappingTarget Comment oldComment);
}
