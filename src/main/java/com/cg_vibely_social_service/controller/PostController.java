package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.PostRequestDto;
import com.cg_vibely_social_service.payload.response.PostResponseDto;
import com.cg_vibely_social_service.service.PostService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final HttpServletRequest request;

    @PostMapping
    public ResponseEntity<?> submitPost(@Valid @RequestBody PostRequestDto postRequestDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                User user = (User) request.getSession().getAttribute("currentUser");
                postRequestDto.setUserId(user.getId());
                postService.save(postRequestDto);
                return new ResponseEntity<>("success", HttpStatus.CREATED);
            }catch (Exception exception){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }
    @GetMapping
    public ResponseEntity<?> showAllPost(){
    List<PostResponseDto> postResponseDtos = postService.findAll();
    return new ResponseEntity<>(postResponseDtos, HttpStatus.OK);
}

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deleteParticularPost(@PathVariable("postId") Long postId){
    postService.deleteById(postId);
    return new ResponseEntity<>(HttpStatus.OK);
}

    @PutMapping
    public ResponseEntity<?> updateParticularPost(@Valid @RequestBody PostRequestDto postRequestDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                User user = (User) request.getSession().getAttribute("currentUser");
                postRequestDto.setUserId(user.getId());
                postService.update(postRequestDto);
                return new ResponseEntity<>("success", HttpStatus.CREATED);
            }catch (Exception exception){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }
}
