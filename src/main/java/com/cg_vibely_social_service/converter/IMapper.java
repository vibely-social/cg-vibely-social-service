package com.cg_vibely_social_service.converter;


import com.cg_vibely_social_service.entity.Feed.FeedItem;
import com.cg_vibely_social_service.payload.request.NewPostRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IMapper {
    IMapper INSTANCE = Mappers.getMapper(IMapper.class);
    @Mapping(target = "authorId", source="newPostRequestDto.authorId")
    @Mapping(target = "content", source="newPostRequestDto.content")
    @Mapping(target = "privacy", source="newPostRequestDto.privacy")
    FeedItem newPostConvert(NewPostRequestDto newPostRequestDto);
}
