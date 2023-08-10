package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.dto.post.request.PostDtoRequest;
import com.cg_vibely_social_service.dto.post.response.PostDtoResponse;
import com.cg_vibely_social_service.entity.Post;
import com.cg_vibely_social_service.repository.PostRepository;
import com.cg_vibely_social_service.service.Impl.PostServiceImpl;
import com.cg_vibely_social_service.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/postService")
public class PostController {

    @Autowired
    PostServiceImpl postServiceImpl;


    @PostMapping("/save")
    public ResponseEntity<?> submitPost(@RequestBody PostDtoRequest body){
        Post post = postServiceImpl.submitPostToDB(body);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
    @GetMapping("/getPost")
    public ResponseEntity<?> showAllPost(){
    List<PostDtoResponse> postDtoResponses = postServiceImpl.findAll();
    return new ResponseEntity<>(postDtoResponses, HttpStatus.OK);
}

//    @DeleteMapping("/delete")
//    public List<Post> deleteParticularPost(@PathVariable("postID") Long postID){
//        List<Post> result = postServiceImpl.deletePostFromDB(postID);
//        return result;
//    }
}
