package com.cg_vibely_social_service.convert;

import com.cg_vibely_social_service.dto.post.request.PostDtoRequest;
import com.cg_vibely_social_service.dto.post.response.PostDtoResponse;
import com.cg_vibely_social_service.entity.Post;
import com.cg_vibely_social_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostConvert {
    private final UserRepository userRepository;
    public Post dtoToEntityResponse(PostDtoResponse postDtoResponse){
        Post post = new Post();
        BeanUtils.copyProperties(postDtoResponse, post);
        return post;
    }
    public Post dtoToEntityRequest(PostDtoRequest postDtoRequest){
        return Post.builder()
                .id(postDtoRequest.getId())
                .dateTime(postDtoRequest.getDateTime())
                .privacy(postDtoRequest.getPrivacy())
                .textContent(postDtoRequest.getTextContent())
                .user(userRepository.findById(postDtoRequest.getUserDtoRequest().getId()).orElse(null))
                .build();
//        Post post = new Post();
//        BeanUtils.copyProperties(postDtoRequest, post);
//
//        return post;
    }


    public PostDtoResponse entityToDto(Post post){
        PostDtoResponse postDtoResponse = new PostDtoResponse();
        BeanUtils.copyProperties(post, postDtoResponse);
        return postDtoResponse;
    }

    public List<PostDtoResponse> entitiesToDtos(List<Post> posts) {
        return posts.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }
}
