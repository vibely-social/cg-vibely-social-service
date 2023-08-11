package com.cg_vibely_social_service.service.impl;
import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.PostRequestDto;
import com.cg_vibely_social_service.entity.Post;
import com.cg_vibely_social_service.payload.response.PostResponseDto;
import com.cg_vibely_social_service.repository.PostRepository;
import com.cg_vibely_social_service.repository.UserRepository;
import com.cg_vibely_social_service.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final Converter<PostRequestDto,Post> postRequestDtoConverter;
    private final Converter<PostResponseDto,Post> postResponseDtoConverter;


    @Override
    public List<PostResponseDto> findByUser(User user) {
        List<Post> posts = postRepository.findByUser(user);
        List<PostResponseDto> postDtoResponses = postResponseDtoConverter.revert(posts);
        return postDtoResponses;
    }
    public Post submitPostToDB(PostRequestDto postRequestDto){
        Post post = postRequestDtoConverter.convert(postRequestDto);
        return postRepository.save(post);
    }
    @Override
    public void deleteByPostId(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public PostRequestDto updateByPostId(PostRequestDto postRequestDto) {
        Post post = postRequestDtoConverter.convert(postRequestDto);
        postRepository.save(post);
        return postRequestDto;
    }

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public Post update(Long id, Post post) throws EntityNotFoundException {
        return null;
    }




 }