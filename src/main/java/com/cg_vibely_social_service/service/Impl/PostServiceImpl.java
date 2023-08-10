package com.cg_vibely_social_service.service.Impl;

import com.cg_vibely_social_service.convert.PostConvert;
import com.cg_vibely_social_service.dto.post.request.PostDtoRequest;
import com.cg_vibely_social_service.dto.post.response.PostDtoResponse;
import com.cg_vibely_social_service.entity.Post;
import com.cg_vibely_social_service.repository.PostRepository;
import com.cg_vibely_social_service.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostConvert postConvert;
    @Override
    public List<PostDtoResponse> findAll() {
        List<Post> posts = new ArrayList<>();
        List<PostDtoResponse> postDtoResponses = postConvert.entitiesToDtos(posts);
        return postDtoResponses;
    }
    public Post submitPostToDB(PostDtoRequest postDtoRequest){
        Post post = postConvert.dtoToEntityRequest(postDtoRequest);
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Object save(Object o) {
        return null;
    }

    @Override
    public Object update(Long id, Object o) throws EntityNotFoundException {
        return null;
    }

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public Post update(Long id, Post post) throws EntityNotFoundException {
        return null;
    }

    @Override
    public void remove(Long id) throws EntityNotFoundException {

    }


//    public List<PostDtoResponse> retrievePostFromDB(){
//        List<Post> posts = new ArrayList<>();
//        List<PostDtoResponse> postDtoResponses = postConvert.entitiesToDtos(posts);
//        return postDtoResponses;
//    }

//    public List<Post> deletePostFromDB(Long postID){
//        postRepository.deleteById(postID);
//        List<Post> result = retrievePostFromDB();
//        return result;
//    }
}