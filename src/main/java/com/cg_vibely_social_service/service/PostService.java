package com.cg_vibely_social_service.service;

import com.cg_vibely_social_service.payload.request.PostRequestDto;
import com.cg_vibely_social_service.entity.Post;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

public interface PostService {
    List<PostRequestDto> findByUserId(Long userId);

    Post save(Post post);

    Post update(Long id, Post post) throws EntityNotFoundException;
    Post submitPostToDB(PostRequestDto postRequestDto);
    void deleteByPostId(Long postId);
    PostRequestDto updateByPostId(PostRequestDto postRequestDto);

 }