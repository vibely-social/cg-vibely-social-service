package com.cg_vibely_social_service.service;

import com.cg_vibely_social_service.dto.post.request.PostDtoRequest;
import com.cg_vibely_social_service.dto.post.response.PostDtoResponse;
import com.cg_vibely_social_service.entity.Post;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface PostService extends GenericService {
    List<PostDtoResponse> findAll();

    Post save(Post post);

    Post update(Long id, Post post) throws EntityNotFoundException;
    Post submitPostToDB(PostDtoRequest postDtoRequest);
}