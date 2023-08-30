package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.payload.response.CommentResponseDto;
import com.cg_vibely_social_service.payload.response.LikeResponseDto;
import com.cg_vibely_social_service.service.CommentService;
import com.cg_vibely_social_service.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final LikeService likeService;
    @PostMapping("/{postId}/comment")
    public ResponseEntity<?> newComment(@PathVariable("postId") Long postId,
                                        @RequestParam(value = "file",required = false) MultipartFile file ,
                                        String newComment) {
        try {
            CommentResponseDto commentResponseDto = commentService.newComment(postId,newComment,file);
            return new ResponseEntity<>(commentResponseDto,HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @PostMapping("/{postId}/reply/{commentId}")
    public ResponseEntity<?> newReply(@PathVariable("postId") Long postId,
                                        @PathVariable("commentId") Long commentId,
                                        @RequestParam(value = "file",required = false) MultipartFile file ,
                                        String reply) {
        try {
            CommentResponseDto commentResponseDto = commentService.replyComment(postId,commentId,reply,file);
            return new ResponseEntity<>(commentResponseDto,HttpStatus.CREATED);
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

    @GetMapping("/{postId}/like/{commentId}")
    public ResponseEntity<?> likeComment(@PathVariable("postId") Long postId,
                                         @PathVariable("commentId") Long commentId) {
        try {
            LikeResponseDto likeResponseDto = likeService.likeComment(postId,commentId);
            return new ResponseEntity<>(likeResponseDto,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @GetMapping("/{postId}/like/{commentId}/{replyId}")
    public ResponseEntity<?> likeReply(@PathVariable("postId") Long postId,
                                       @PathVariable("commentId") Long commentId,
                                       @PathVariable("replyId") Long replyId) {
        try {
            LikeResponseDto likeResponseDto = likeService.likeReply(replyId,commentId,postId);
            return new ResponseEntity<>(likeResponseDto,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
