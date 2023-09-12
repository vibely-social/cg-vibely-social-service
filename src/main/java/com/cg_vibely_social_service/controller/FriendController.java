package com.cg_vibely_social_service.controller;


import com.cg_vibely_social_service.payload.response.FriendRequestResponse;
import com.cg_vibely_social_service.payload.response.FriendResponseDto;
import com.cg_vibely_social_service.service.FriendRequestService;
import com.cg_vibely_social_service.service.FriendService;
import com.cg_vibely_social_service.service.StatusService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
@Tag(
        name = "Friend",
        description = "API endpoints for friends functions"
)
public class FriendController {
    private final FriendService friendService;
    private final FriendRequestService friendRequestService;
    private final StatusService statusService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Get all friends of a user",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the user",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get friend list successfully",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = FriendResponseDto.class)
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<?> getFriendList(@PathVariable("id") Long id) {
        List<FriendResponseDto> friendList = friendService.findFriendsByUserId(id);
        return new ResponseEntity<>(friendList, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    @Operation(
            summary = "Send friend request to a user",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the user that receive friend request",
                            in = ParameterIn.PATH,
                            required = true
                    ),
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Friend request sent successfully"
                    )
            }
    )
    public ResponseEntity<?> sendFriendRequest(@PathVariable("id") Long friendId) {
        friendRequestService.saveFriendRequest(friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/accept/{id}")
    @Operation(
            summary = "Accept friend request",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the user that sent friend request",
                            in = ParameterIn.PATH,
                            required = true
                    ),
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Request accept successfully"
                    )
            }
    )
    public ResponseEntity<?> acceptFriendRequest(@PathVariable("id") Long friendId) {
        friendRequestService.acceptFriendRequest(friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/requested") //requests from current user
    @Operation(
            summary = "Get all friend requests sent by current user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get friends request successfully",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = FriendRequestResponse.class)
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<?> getRequestedFriend() {
        List<FriendRequestResponse> friendRequestList
                = friendRequestService.findAllFriendRequestSent();
        return new ResponseEntity<>(friendRequestList, HttpStatus.OK);
    }

    @GetMapping("/request") //requests by other user
    @Operation(
            summary = "Get all friend requests sent to current user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get friends request successfully",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = FriendRequestResponse.class)
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<?> getFriendRequest() {
        List<FriendRequestResponse> friendRequestResponseList
                = friendRequestService.findAllFriendRequest();
        return new ResponseEntity<>(friendRequestResponseList, HttpStatus.OK);
    }

    @DeleteMapping("/requests/{id}")
    @Operation(
            summary = "Deny a friend requests",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of current user",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deny friends request successfully"
                    )
            }
    )
    public ResponseEntity<?> denyFriendRequest(@PathVariable("id") Long friendId) {
        friendRequestService.denyFriendRequest(friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Unfriend a user",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the user being unfriended",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Unfriend successfully"
                    )
            }
    )
    public ResponseEntity<?> removeFriend(@PathVariable("id") Long friendId) {
        friendService.removeFriend(friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/status")
    @Operation(
            summary = "Get status online or offline of friend list",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "List of email address that was friend with user",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(type = "string")
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get status successfully"
                    )
            }
    )
    public ResponseEntity<?> getFriendStatus(@RequestBody List<String> emails) {
        HashMap<String, Boolean> friendStatus = statusService.getStatus(emails);
        return new ResponseEntity<>(friendStatus, HttpStatus.OK);
    }
}