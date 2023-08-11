package com.cg_vibely_social_service.converter.impl;

import com.vibely_social.converter.Converter;
import com.cg_vibely_social_service.entity.Post;
import com.cg_vibely_social_service.payload.request.PostRequestDto;
import com.vibely_social.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostRequestDtoConverter implements Converter<PostRequestDto, Post> {
    private final UserRepository userRepository;
    @Override
    public Post convert(PostRequestDto source) {
        return Post.builder()
                .createdAt(source.getDateTime())
                .privacy(source.getPrivacy())
                .textContent(source.getTextContent())
                .user(userRepository.findById(source.getUserId()).orElse(null))
                .build();
    }
    @Override
    public PostRequestDto revert(Post target) {
        return null;
    }

    @Override
    public List<Post> convert(List<PostRequestDto> sources) {
        return null;
    }

    @Override
    public List<PostRequestDto> revert(List<Post> targets) {
        return null;
    }
}
