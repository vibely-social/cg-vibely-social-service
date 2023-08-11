package com.vibely_social.service.impl;
import com.vibely_social.converter.PostConvert;
import com.vibely_social.converter.impl.PostRequestDtoConverter;
import com.vibely_social.converter.impl.PostResponseDtoConverter;
import com.vibely_social.entity.User;
import com.vibely_social.payload.request.PostRequestDto;
import com.vibely_social.entity.Post;
import com.vibely_social.payload.response.PostResponseDto;
import com.vibely_social.repository.PostRepository;
import com.vibely_social.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final PostRequestDtoConverter postRequestDtoConverter;

    private final PostResponseDtoConverter postResponseDtoConverter;

    @Override
    public List<PostResponseDto> findByUserId(Long userId) {
        User user = user
        List<Post> posts = postRepository.findByUserId(userId);
        List<PostResponseDto> postDtoResponses = postResponseDtoConverter.revert(posts);
        return postDtoResponses;
    }

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public Post update(Long id, Post post) throws EntityNotFoundException {
        return null;
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
        Post post = Post.builder()
                .id(postRequestDto.getId())
                .privacy(postRequestDto.getPrivacy())
                .textContent(postRequestDto.getTextContent())
                .createdAt(postRequestDto.getDateTime())
                .build();
        postRepository.save(post);
        return postConvert.entityToDto(post);
    }
 }