package com.cg_vibely_social_service.controller;


import com.cg_vibely_social_service.payload.request.FriendRequestDto;
import com.cg_vibely_social_service.payload.response.FriendResponseDto;
import com.cg_vibely_social_service.service.FriendRequestService;
import com.cg_vibely_social_service.service.FriendService;
import com.cg_vibely_social_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final UserService userService;
    private final FriendService friendService;
    private final FriendRequestService friendRequestService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getFriendList (@PathVariable("id") Long id ) {
        if (userService.findById(id) != null) {
            List<FriendResponseDto> friendList = friendService.findFriendsByUserId(id);
            return new ResponseEntity<>(friendList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //Tạo lời mời
    @PostMapping("/request")
    public ResponseEntity<?> saveFriendRequest(@RequestBody FriendRequestDto friendRequestDto) {
       friendRequestService.saveFriendRequest(friendRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Tìm lời mời đã gửi kết bạn
    @GetMapping("/requested/{id}")
    public ResponseEntity<?> getRequestedFriend (@PathVariable("id") Long id) {
        List<FriendRequestDto> friendRequestList = friendRequestService.findAllFriendRequestByUserId(id);
        return new ResponseEntity<>(friendRequestList, HttpStatus.OK);
    }

    //Tìm lời mời kết bạn
    @GetMapping("/request/{id}")
    public ResponseEntity<?> getFriendRequest (@PathVariable("id") Long id) {
        List<FriendRequestDto> friendRequestList = friendRequestService.findAllFriendRequestByFriendId(id);
        return new ResponseEntity<>(friendRequestList, HttpStatus.OK);
    }

}
