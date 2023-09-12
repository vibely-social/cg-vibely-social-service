package com.cg_vibely_social_service.converter.impl;

import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.entity.Media;
import com.cg_vibely_social_service.payload.response.MediaResponseDto;
import com.cg_vibely_social_service.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MediaResponseDtoConverter implements Converter<MediaResponseDto, Media> {
    private final ImageService imageService;

    @Override
    public Media convert(MediaResponseDto source) {
        return null;
    }

    @Override
    public MediaResponseDto revert(Media target) {
        return MediaResponseDto.builder()
                .id(target.getId())
                .userId(target.getUserId())
                .postId(target.getPostID())
                .imageUrl(target.getFileName())
                .build();
    }

    @Override
    public List<Media> convert(List<MediaResponseDto> sources) {
        return null;
    }

    @Override
    public List<MediaResponseDto> revert(List<Media> targets) {
        List<MediaResponseDto> mediaResponseDtoList = new ArrayList<>();
        for (Media media : targets) {
            mediaResponseDtoList.add(MediaResponseDto.builder()
                    .id(media.getId())
                    .userId(media.getUserId())
                    .postId(media.getPostID())
                    .imageUrl(imageService.getImageUrl(media.getFileName()))
                    .build());
        }
        ;
        return mediaResponseDtoList;
    }
}
