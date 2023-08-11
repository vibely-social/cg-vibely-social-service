package com.vibely_social.service;

import com.vibely_social.payload.request.PostRequestDto;
import com.vibely_social.entity.Post;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

public interface PostService {
    List<PostRequestDto> findByUserId(Long userId);

    Post save(Post post);

    Post update(Long id, Post post) throws EntityNotFoundException;
    Post submitPostToDB(com.vibely_social.payload.request.PostRequestDto postRequestDto);
    void deleteByPostId(Long postId);
    PostRequestDto updateByPostId(PostRequestDto postRequestDto);

 }