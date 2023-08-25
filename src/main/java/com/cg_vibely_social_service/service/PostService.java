package com.cg_vibely_social_service.service;

import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.PostRequestDto;
import com.cg_vibely_social_service.payload.response.PostResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    List<PostResponseDto> findByAuthorId(Long authorId);
    PostResponseDto findById(Long id);

    List<PostResponseDto> findAll();

    void save(PostRequestDto postRequestDto);

    void deleteById(Long postId);

    PostResponseDto update(PostRequestDto postRequestDto);

    void newPost(String source, List<String> files) throws JsonProcessingException;

    void newPost(String source) throws JsonProcessingException;

    List<PostResponseDto> getNewestPost(int page);

}
