package com.cg_vibely_social_service.controller;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.PostRequestDto;
import com.cg_vibely_social_service.entity.Post;
import com.cg_vibely_social_service.payload.response.PostResponseDto;
import com.cg_vibely_social_service.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostServiceImpl postServiceImpl;

    @PostMapping
    public ResponseEntity<?> submitPost(@RequestBody PostRequestDto body){
        Post post = postServiceImpl.submitPostToDB(body);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<?> showAllPost(@RequestBody User user){
    List<PostResponseDto> postResponseDtos = postServiceImpl.findByUser(user);
    return new ResponseEntity<>(postResponseDtos, HttpStatus.OK);
}

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deleteParticularPost(@PathVariable("postId") Long postId){
    postServiceImpl.deleteByPostId(postId);
    return new ResponseEntity<>(HttpStatus.OK);
}

    @PutMapping
    public ResponseEntity<?> updateParticularPost(@RequestBody PostRequestDto postRequestDto){
        PostRequestDto postDtoResponse = postServiceImpl.updateByPostId(postRequestDto);
        return new ResponseEntity<>(postDtoResponse, HttpStatus.OK);
    }
}
