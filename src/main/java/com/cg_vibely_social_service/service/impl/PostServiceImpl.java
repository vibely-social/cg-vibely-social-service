package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.converter.IMapper;
import com.cg_vibely_social_service.entity.Feed.Feed;
import com.cg_vibely_social_service.entity.Feed.FeedItem;
import com.cg_vibely_social_service.entity.Post;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.NewPostRequestDto;
import com.cg_vibely_social_service.payload.request.PostRequestDto;
import com.cg_vibely_social_service.payload.response.PostResponseDto;
import com.cg_vibely_social_service.repository.FeedRespository;
import com.cg_vibely_social_service.repository.PostRepository;
import com.cg_vibely_social_service.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final Converter<PostResponseDto, Post> postResponseDtoConverter;
    private final Converter<PostRequestDto, Post> postRequestDtoConverter;


    @Override
    public List<PostResponseDto> findByUser(User user) {
        List<Post> posts = postRepository.findByUser(user);
        return postResponseDtoConverter.revert(posts);
    }

    @Override
    public List<PostResponseDto> findAll() {
        List<Post> posts = postRepository.findAll();
        return postResponseDtoConverter.revert(posts);
    }

    @Override
    public void save(PostRequestDto postRequestDto) {
        Post post = postRequestDtoConverter.convert(postRequestDto);
        postRepository.save(post);
    }

    @Override
    public void deleteById(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public PostResponseDto update(PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postRequestDto.getId()).orElseThrow();
        post.setPrivacy(postRequestDto.getPrivacy());
        post.setTextContent(postRequestDto.getTextContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setEdited(true);
        postRepository.save(post);
        return postResponseDtoConverter.revert(post);
    }
}
