package com.cg_vibely_social_service.service;

import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.PostRequestDto;
import com.cg_vibely_social_service.payload.response.PostResponseDto;

import java.util.List;

public interface PostService {
    List<PostResponseDto> findByUser(User user);

    List<PostResponseDto> findAll();

    void save(PostRequestDto postRequestDto);

    void deleteById(Long postId);

    PostResponseDto update(PostRequestDto postRequestDto);

}
