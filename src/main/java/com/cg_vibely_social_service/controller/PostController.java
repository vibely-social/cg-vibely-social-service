package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.PostRequestDto;
import com.cg_vibely_social_service.payload.response.LikeResponseDto;
import com.cg_vibely_social_service.payload.response.PostResponseDto;
import com.cg_vibely_social_service.repository.PostRepository;
import com.cg_vibely_social_service.service.CommentService;
import com.cg_vibely_social_service.service.ImageService;
import com.cg_vibely_social_service.service.LikeService;
import com.cg_vibely_social_service.service.MediaService;
import com.cg_vibely_social_service.service.NotificationService;
import com.cg_vibely_social_service.service.PostService;

import com.cg_vibely_social_service.service.UserService;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
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
@Tag(
        name = "Post",
        description = "API endpoints for Post functions"
)
public class PostController {

    private final PostService postService;
    private final HttpServletRequest request;
    private final ImageService imageService;
    private final LikeService likeService;

    private final UserService userService;
    private final MediaService mediaService;

    @PostMapping
    @Operation(
            summary = "Submit new post",
            parameters = {
                    @Parameter(
                            name = "files",
                            description = "List of media filename",
                            in = ParameterIn.QUERY
                    ),
                    @Parameter(
                            name = "newPost",
                            description = "Post content and metadata",
                            in = ParameterIn.QUERY
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Post created",
                            content = @Content(
                                    schema = @Schema(implementation = PostResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "Empty post not allowed"
                    )
            }
    )
    public ResponseEntity<?> submitPost(@RequestParam(value = "files", required = false) List<MultipartFile> files,
                                        @RequestParam(value = "newPostDTO") String newPostDTO) {

        try {
            PostResponseDto postResponseDto;
            if (files != null) {
                if (newPostDTO == null)
                    return new ResponseEntity<>("Can't create empty post", HttpStatus.NOT_ACCEPTABLE);
                List<String> fileNames = imageService.save(files);
                postResponseDto = postService.newPost(newPostDTO, fileNames);

                //reset cache after user post new media
                Long user_id = userService.getCurrentUser().getId();
                mediaService.resetMediaCache(user_id);
            } else {
                postResponseDto = postService.newPost(newPostDTO);
            }
            return new ResponseEntity<>(postResponseDto, HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping
    @Operation(
            summary = "show post on new feed",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get posts successfully",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = PostResponseDto.class)
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<?> showPosts() {
        List<PostResponseDto> feedItemResponseDTOs = postService.getNewestPost(0);
        Collections.shuffle(feedItemResponseDTOs, new Random());
        return new ResponseEntity<>(feedItemResponseDTOs, HttpStatus.OK);
    }

    @GetMapping("/like/{postId}")
    @Operation(
            summary = "Like post",
            parameters = {
                    @Parameter(
                            name = "postId",
                            description = "ID of the post being liked",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "like post successfully",
                            content = @Content(
                                    schema = @Schema(implementation = LikeResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "failed to like"
                    )
            }
    )
    public ResponseEntity<?> likePost(@PathVariable Long postId) {
        try {
            LikeResponseDto likeResponseDto = likeService.likePost(postId);
            return new ResponseEntity<>(likeResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/{postId}")
    @Operation(
            summary = "Delete post by Id",
            parameters = {
                    @Parameter(
                            name = "postId",
                            description = "Id of the post being deleted",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Post deleted successfully"
                    )
            }
    )
    public ResponseEntity<?> deleteParticularPost(@PathVariable("postId") Long postId) {
        postService.deleteById(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    @Operation(
            summary = "Get post by Id",
            parameters = {
                    @Parameter(
                            name = "postId",
                            description = "Id of the post",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get post successfully",
                            content = @Content(
                                    schema = @Schema(implementation = PostResponseDto.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> getPost(@PathVariable("postId") Long postId) {
        PostResponseDto postResponseDto = postService.findById(postId);
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    @GetMapping("/user/{authorId}")
    @Operation(
            summary = "Get all posts by user Id",
            parameters = {
                    @Parameter(
                            name = "authorId",
                            description = "Id of the user",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get all posts successfully",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = PostResponseDto.class)
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<?> getPostsByUser(@PathVariable("authorId") Long authorId) {
        List<PostResponseDto> postResponseDto = postService.findByAuthorId(authorId);
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    @PutMapping
    @Operation(
            summary = "Update a post",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Post Request that contain updated data",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = PostRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get all posts successfully"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Data not acceptable"
                    )
            }
    )
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

    @GetMapping("/user/{authorId}/{page}")
    @Operation(
            summary = "Get post by paging",
            parameters = {
                    @Parameter(
                            name = "authorId",
                            description = "Id of the user that has post being get",
                            in = ParameterIn.PATH,
                            required = true
                    ),
                    @Parameter(
                            name = "page",
                            description = "Number of next page",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get post successfully",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = PostResponseDto.class)
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<?> getPagingPost(@PathVariable("authorId") Long authorId, @PathVariable("page") int page) {
        List<PostResponseDto> postResponseDto = postService.getPostPagingByAuthor(authorId, page);
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }
}

