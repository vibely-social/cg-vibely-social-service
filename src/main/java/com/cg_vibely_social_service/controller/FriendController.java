package com.cg_vibely_social_service.controller;


import com.cg_vibely_social_service.payload.request.FriendRequestDto;
import com.cg_vibely_social_service.payload.request.ResolveRequestDto;
import com.cg_vibely_social_service.payload.response.FriendResponseDto;
import com.cg_vibely_social_service.service.FriendRequestService;
import com.cg_vibely_social_service.service.FriendService;
import com.cg_vibely_social_service.service.StatusService;
import com.cg_vibely_social_service.service.UserService;
import com.cg_vibely_social_service.service.impl.UserImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final UserService userService;
    private final FriendService friendService;
    private final FriendRequestService friendRequestService;
    private final StatusService statusService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getFriendList(@PathVariable("id") Long id) {
        UserImpl user = userService.getCurrentUser();
        if (user != null) {
            List<FriendResponseDto> friendList = friendService.findFriendsByUserId(id);
            return new ResponseEntity<>(friendList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/request")
    public ResponseEntity<?> saveFriendRequest(@RequestBody FriendRequestDto friendRequestDto) {
        friendRequestService.saveFriendRequest(friendRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/requestResolve")
    public ResponseEntity<?> resolveRequest(@RequestBody ResolveRequestDto resolveRequestDto) {
        friendRequestService.resolveRequest(resolveRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/requested/{id}")
    public ResponseEntity<?> getRequestedFriend(@PathVariable("id") Long id) {
        List<FriendRequestDto> friendRequestList = friendRequestService.findAllFriendRequestByUserId(id);
        return new ResponseEntity<>(friendRequestList, HttpStatus.OK);
    }

    @GetMapping("/request/{id}")
    public ResponseEntity<?> getFriendRequest(@PathVariable("id") Long id) {
        List<FriendRequestDto> friendRequestList = friendRequestService.findAllFriendRequestByFriendId(id);
        return new ResponseEntity<>(friendRequestList, HttpStatus.OK);
    }

    @PostMapping("/status")
    public ResponseEntity<?> getFriendStatus (@RequestBody List<String> emails){
        HashMap<String, Boolean> friendStatus = statusService.getStatus(emails);
        return new ResponseEntity<>(friendStatus, HttpStatus.OK);
    }
}