package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.PostRequestDto;
import com.cg_vibely_social_service.payload.response.PostResponseDto;
import com.cg_vibely_social_service.service.ImageService;
import com.cg_vibely_social_service.service.PostService;

import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final HttpServletRequest request;
    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<?> submitPost(@RequestParam(value = "files",required = false) List<MultipartFile> files,
                                        @RequestParam(value = "newPostDTO") String newPostDTO){
        try {
            if(files != null) {
                List<String> fileNames = imageService.save(files);
                postService.newPost(newPostDTO, fileNames);
            }
            else{
                if(newPostDTO != null) postService.newPost(newPostDTO);
                else{
                    return new ResponseEntity<>("Can't create empty post", HttpStatus.NOT_ACCEPTABLE);
                }
            }
            return new ResponseEntity<>("Your post was created!",HttpStatus.CREATED);
        }
        catch (
                JsonMappingException e){
            return new ResponseEntity<>("Invalid new post data",HttpStatus.NOT_ACCEPTABLE);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.PAYMENT_REQUIRED);
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable("postId") Long postId) {
        return new ResponseEntity<>(postService.findById(postId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> showPosts(){
        List<PostResponseDto> feedItemResponseDTOs = postService.getNewestPost(0);
        Collections.shuffle(feedItemResponseDTOs,new Random());
        return new ResponseEntity<>(feedItemResponseDTOs, HttpStatus.OK);
}

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deleteParticularPost(@PathVariable("postId") Long postId) {
        postService.deleteById(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateParticularPost(@Valid @RequestBody PostRequestDto postRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try {
                User user = (User) request.getSession().getAttribute("currentUser");
                postRequestDto.setAuthorId(user.getId());
                postService.update(postRequestDto);
                return new ResponseEntity<>("success", HttpStatus.CREATED);
            } catch (Exception exception) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }
}
