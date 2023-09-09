package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.payload.response.FollowResponseDto;
import com.cg_vibely_social_service.service.impl.FollowServiceImpl;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
@Tag(
        name = "Following",
        description = "API endpoints for following functions"
)
public class FollowController {
    private final FollowServiceImpl followService;

    @GetMapping("/{id}/follower")
    @Operation(
            summary = "Get all follower of a user",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Id of the user",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get follower successfully",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = FollowResponseDto.class)
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<List<FollowResponseDto>> getFollower(@PathVariable ("id") Long userId) {
        List<FollowResponseDto> followerResponseDtoList = followService.getAllFollowerByUserId(userId);
        return new ResponseEntity<>(followerResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}/following")
    @Operation(
            summary = "Get all the user that the current user is following",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Id of the user",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get follower successfully",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = FollowResponseDto.class)
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<List<FollowResponseDto>> getFollowing(@PathVariable ("id") Long userId){
        List<FollowResponseDto> followingResponseDtoList = followService.getAllFollowingByUserId(userId);
        return new ResponseEntity<>(followingResponseDtoList, HttpStatus.OK);
    }

    @PostMapping("/{userId}/{targetId}")
    @Operation(
            summary = "Follow a user",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "Id of the user",
                            in = ParameterIn.PATH,
                            required = true
                    ),
                    @Parameter(
                            name = "targetId",
                            description = "Id of the user being follow",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get follower successfully"
                    )
            }
    )
    public ResponseEntity<?> addFollow (@PathVariable ("userId") Long userId,
                                        @PathVariable ("targetId") Long targetId){
        followService.addFollowByUserAndTarget(userId, targetId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{followedUserId}/{BeingFollowedUserId}")
    @Operation(
            summary = "Unfollow a user",
            parameters = {
                    @Parameter(
                            name = "followedUserId",
                            description = "Id of the user",
                            in = ParameterIn.PATH,
                            required = true
                    ),
                    @Parameter(
                            name = "BeingFollowedUserId",
                            description = "Id of the user being unfollow",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Unfollow successfully"
                    )
            }
    )
    public ResponseEntity<?> UnFollow (@PathVariable ("followedUserId") Long followedUserId,
                                       @PathVariable ("BeingFollowedUserId") Long BeingFollowedUserId){
        followService.unFollowByUserAndTarget(BeingFollowedUserId, followedUserId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
