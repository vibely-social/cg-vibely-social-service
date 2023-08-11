package com.vibely_social.controller;
import com.vibely_social.payload.request.PostRequestDto;
import com.vibely_social.entity.Post;
import com.vibely_social.payload.response.PostResponseDto;
import com.vibely_social.service.impl.PostServiceImpl;
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
    public ResponseEntity<?> submitPost(@RequestBody com.vibely_social.payload.request.PostRequestDto body){
        Post post = postServiceImpl.submitPostToDB(body);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<?> showAllPost(@PathVariable("userId") Long userId){
    List<PostResponseDto> postResponseDtos = postServiceImpl.findByUserId(userId);
    return new ResponseEntity<>(postResponseDtos, HttpStatus.OK);
}

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deleteParticularPost(@PathVariable("postId") Long postId){
    postServiceImpl.deleteByPostId(postId);
    return new ResponseEntity<>(HttpStatus.OK);
}

    @PutMapping
    public ResponseEntity<?> updateParticularPost(@RequestBody com.vibely_social.payload.request.PostRequestDto postRequestDto){
        PostRequestDto postDtoResponse = postServiceImpl.updateByPostId(postRequestDto);
        return new ResponseEntity<>(postDtoResponse, HttpStatus.OK);
    }

}
