package com.cg_vibely_social_service.converter;

import com.cg_vibely_social_service.entity.Feed.FeedItem;
import com.cg_vibely_social_service.payload.request.PostRequestDto;
import com.cg_vibely_social_service.payload.response.PostResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IPostMapper {
    IPostMapper INSTANCE = Mappers.getMapper(IPostMapper.class);

    @Mapping(target = "authorId", source="postRequestDto.authorId")
    @Mapping(target = "content", source="postRequestDto.content")
    @Mapping(target = "privacy", source="postRequestDto.privacy")
    @Mapping(target = "tags", source="postRequestDto.tags")
    @Mapping(target = "subscribers", source="postRequestDto.subscribers")
    FeedItem newPostConvert(PostRequestDto postRequestDto);

    @Mapping(target = "author.id", source="feedItem.authorId")
    @Mapping(target = "content", source="feedItem.content")
    @Mapping(target = "privacy", source="feedItem.privacy")
    @Mapping(target = "createdDate", source="feedItem.createdDate")
    PostResponseDto postResponseDto(FeedItem feedItem);
}
