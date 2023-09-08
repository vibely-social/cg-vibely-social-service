package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.payload.request.CommentRequestDto;
import com.cg_vibely_social_service.payload.request.ReplyRequestDto;
import com.cg_vibely_social_service.payload.response.CommentResponseDto;
import com.cg_vibely_social_service.payload.response.LikeResponseDto;
import com.cg_vibely_social_service.service.CommentService;
import com.cg_vibely_social_service.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(
        name = "Comment",
        description = "API endpoints for comment functions"
)
public class CommentController {

    private final CommentService commentService;

    private final LikeService likeService;
    @PostMapping("/{postId}/comment")
    @Operation(
            summary = "Post and save new comment",
            parameters = {
                    @Parameter(
                            name = "postId",
                            description = "ID of the post being commented",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Comment create successfully",
                            content = @Content(
                                    schema = @Schema(implementation = CommentResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "Request body data not acceptable or empty"
                    )
            }
    )
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

    @PutMapping("/{postId}/comment")
    @Operation(
            summary = "Edit comment",
            parameters = {
                    @Parameter(
                            name = "postId",
                            description = "ID of the post that the comment being edit",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Edited comment",
                            content = @Content(
                                    schema = @Schema(implementation = CommentResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "Data not acceptable or empty"
                    )
            }
    )
    public ResponseEntity<?> editComment(@PathVariable("postId") Long postId,
                                        @RequestParam(value = "file",required = false) MultipartFile file ,
                                         @RequestParam(value = "payload") String payload) {
        try {
            CommentRequestDto commentRequestDto = new CommentRequestDto().convert(payload);
            CommentResponseDto commentResponseDto = commentService.editComment(postId,commentRequestDto,file);
            return new ResponseEntity<>(commentResponseDto,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("postId") Long postId,
                                           @PathVariable("commentId") Long commentId) {
        try {
            commentService.deleteComment(postId,commentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @PostMapping("/{postId}/reply/{commentId}")
    @Operation(
            summary = "Reply a comment on the post",
            parameters = {
                    @Parameter(
                            name = "postId",
                            description = "The ID of the post to which the comment is being replied",
                            in = ParameterIn.PATH,
                            required = true
                    ),
                    @Parameter(
                            name = "commentId",
                            description = "The ID of the comment being replied",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Reply post successful",
                            content = @Content(
                                    schema = @Schema(implementation = CommentResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "Reply data not acceptable or empty"
                    )
            }
    )
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
    @PutMapping("/{postId}/reply")
    public ResponseEntity<?> editReply(@PathVariable("postId") Long postId,
                                       @RequestParam(value = "file",required = false) MultipartFile file,
                                       String payload) {
        try {
            ReplyRequestDto replyRequestDto = new ReplyRequestDto().convert(payload);
            CommentResponseDto commentResponseDto = commentService.editReply(postId,replyRequestDto,file);
            return new ResponseEntity<>(commentResponseDto,HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    
    @GetMapping("/{postId}/comments/{sortBy}")
    @Operation(
            summary = "Get all comments of a post",
            parameters = {
                    @Parameter(
                            name = "postId",
                            description = "ID of the post",
                            in = ParameterIn.PATH,
                            required = true
                    ),
                    @Parameter(
                            name = "sortBy",
                            description = "sort option",
                            in = ParameterIn.PATH,
                            required = false
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "get comment successfully",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = CommentResponseDto.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "post id not acceptable"
                    )
            }
    )
    public ResponseEntity<?> getComment(@PathVariable("postId") Long postId,
                                        @PathVariable(value = "sortBy", required = false) String sortBy) {
        try {
            List<CommentResponseDto> commentResponseDTOs = commentService.getComments(postId);
            return new ResponseEntity<>(commentResponseDTOs,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/{postId}/like/{commentId}")
    @Operation(
            summary = "Like the comment on the post",
            parameters = {
                    @Parameter(
                            name = "postId",
                            description = "The ID of the post",
                            in = ParameterIn.PATH,
                            required = true
                    ),
                    @Parameter(
                            name = "commentId",
                            description = "The ID of the comment that is liked",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Like comment successfully",
                            content = @Content(
                                    schema = @Schema(implementation = LikeResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "post id or comment id not acceptable"
                    )
            }
    )
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
    @Operation(
            summary = "Like a reply on a comment",
            parameters = {
                    @Parameter(
                            name = "postId",
                            description = "The ID of the post",
                            in = ParameterIn.PATH,
                            required = true
                    ),
                    @Parameter(
                            name = "commentId",
                            description = "The ID of the comment that contain the reply",
                            in = ParameterIn.PATH,
                            required = true
                    ),
                    @Parameter(
                            name = "replyId",
                            description = "The ID of the reply is liked",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Like reply successfully",
                            content = @Content(
                                    schema = @Schema(implementation = LikeResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "post id or comment id or reply id not acceptable"
                    )
            }
    )
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

    @DeleteMapping("/{postId}/comment/{commentId}/{replyId}")
    public ResponseEntity<?> deleteReply(@PathVariable("postId") Long postId,
                                           @PathVariable("commentId") Long commentId,
                                         @PathVariable("replyId") Long replyId) {
        try {
            commentService.deleteReply(postId,commentId,replyId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
