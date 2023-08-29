package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.entity.Feed.Comment;
import com.cg_vibely_social_service.payload.response.CommentResponseDto;
import com.cg_vibely_social_service.service.CommentService;
import com.cg_vibely_social_service.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    @PostMapping("/{postId}/comment")
    public ResponseEntity<?> newComment(@PathVariable("postId") Long postId,
                                        @RequestParam(value = "file",required = false) MultipartFile file ,
                                        String newComment) {
        try {
            commentService.newComment(postId,newComment,file);
            return new ResponseEntity<>("Your comment was created!",HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @GetMapping("/{postId}/comments")
    public ResponseEntity<?> getComment(@PathVariable("postId") Long postId) {
        try {
            List<CommentResponseDto> commentResponseDTOs = commentService.getComments(postId);
            return new ResponseEntity<>(commentResponseDTOs,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
