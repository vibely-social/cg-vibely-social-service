package com.cg_vibely_social_service.converter;

import com.cg_vibely_social_service.entity.Feed.FeedItem;
import com.cg_vibely_social_service.payload.request.NewPostRequestDto;
import com.cg_vibely_social_service.payload.response.FeedItemResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IFeedMapper {
    IFeedMapper INSTANCE = Mappers.getMapper(IFeedMapper.class);
    @Mapping(target = "authorId", source="newPostRequestDto.authorId")
    @Mapping(target = "content", source="newPostRequestDto.content")
    @Mapping(target = "privacy", source="newPostRequestDto.privacy")
    @Mapping(target = "tags", source="newPostRequestDto.tags")
    FeedItem newPostConvert(NewPostRequestDto newPostRequestDto);

    @Mapping(target = "author.id", source="feedItem.authorId")
    @Mapping(target = "content", source="feedItem.content")
    @Mapping(target = "privacy", source="feedItem.privacy")
    @Mapping(target = "gallery", source="feedItem.gallery")
    @Mapping(target = "createdDate", source="feedItem.createdDate")
    FeedItemResponseDto feedResponseDto(FeedItem feedItem);
    void feedResponseDto(FeedItem feedItem,@MappingTarget FeedItemResponseDto feedItemResponseDto);
    List<FeedItemResponseDto> feedResponseDto(List<FeedItem> feedItems);
}
