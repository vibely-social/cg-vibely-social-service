package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.PostRequestDto;
import com.cg_vibely_social_service.entity.Post;
import com.cg_vibely_social_service.payload.response.PostResponseDto;
import com.cg_vibely_social_service.service.PostService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

//    @Autowired
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<?> submitPost(@RequestBody PostRequestDto body){
        Post post = postService.submitPostToDB(body);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<?> showAllPost(){
    List<PostResponseDto> postResponseDtos = postService.findAll();
    return new ResponseEntity<>(postResponseDtos, HttpStatus.OK);
}

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deleteParticularPost(@PathVariable("postId") Long postId){
    postService.deleteByPostId(postId);
    return new ResponseEntity<>(HttpStatus.OK);
}

    @PutMapping
    public ResponseEntity<?> updateParticularPost(@RequestBody PostRequestDto postRequestDto){
        PostResponseDto postDtoResponse = postService.updateByPostId(postRequestDto);
        return new ResponseEntity<>(postDtoResponse, HttpStatus.OK);
    }

}
