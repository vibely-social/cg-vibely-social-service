package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.entity.Post;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.PostRequestDto;
import com.cg_vibely_social_service.payload.response.PostResponseDto;
import com.cg_vibely_social_service.repository.PostRepository;
import com.cg_vibely_social_service.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final Converter<PostResponseDto,Post> postResponseDtoConverter;
    private final Converter<PostRequestDto,Post> postRequestDtoConverter;



    @Override
    public List<PostResponseDto> findByUser(User user) {
        List<Post> posts = postRepository.findByUser(user);
        List<PostResponseDto> postResponseDtos = postResponseDtoConverter.revert(posts);
        return postResponseDtos;
    }

    @Override
    public List<PostResponseDto> findAll() {
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> postResponseDtos = postResponseDtoConverter.revert(posts);
        return postResponseDtos;
    }

    @Override
    public Post submitPostToDB(PostRequestDto postRequestDto) {
        Post post = postRequestDtoConverter.convert(postRequestDto);
        return postRepository.save(post);
    }

    @Override
    public void deleteByPostId(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public PostResponseDto updateByPostId(PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postRequestDto.getId()).get();
        post.setPrivacy(postRequestDto.getPrivacy());
        post.setTextContent(postRequestDto.getTextContent());
        post.setCreatedAt(LocalDateTime.now());
        return postResponseDtoConverter.revert(post);
    }
}
