package com.cg_vibely_social_service.converter.impl;

import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.entity.Post;
import com.cg_vibely_social_service.payload.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class PostResponseDtoConverter implements Converter<PostResponseDto, Post> {
    @Override
    public Post convert(PostResponseDto source) {
        Post post = new Post();
        BeanUtils.copyProperties(source, post);
        return post;
    }

    @Override
    public PostResponseDto revert(Post target) {
        PostResponseDto postResponseDto = new PostResponseDto();
        BeanUtils.copyProperties(target, postResponseDto);
        return postResponseDto;
    }

    @Override
    public List<Post> convert(List<PostResponseDto> sources) {
        return null;
    }

    @Override
    public List<PostResponseDto> revert(List<Post> targets) {
        return targets.stream()
                .map(this::revert)
                .collect(Collectors.toList());
    }
}
