package com.cg_vibely_social_service.controller;


import com.cg_vibely_social_service.payload.request.FriendRequestDto;
import com.cg_vibely_social_service.payload.response.FriendResponseDto;
import com.cg_vibely_social_service.service.FriendRequestService;
import com.cg_vibely_social_service.service.FriendService;
import com.cg_vibely_social_service.service.UserService;
import com.cg_vibely_social_service.service.impl.UserImpl;
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
        UserImpl user = userService.getCurrentUser();
        if (user != null) {
            List<FriendResponseDto> friendList = friendService.findFriendsByUserId(id);
            return new ResponseEntity<>(friendList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/request")
    public ResponseEntity<?> sendFriendRequest(@RequestParam("id") Long friendId) {
       friendRequestService.saveFriendRequest(friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/accept/{id}")
    public ResponseEntity<?> acceptFriendRequest(@PathVariable("id") Long friendId) {
        friendRequestService.acceptFriendRequest(friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/requested") //requests from current user
    public ResponseEntity<?> getRequestedFriend () {
        UserImpl user = userService.getCurrentUser();
        List<FriendRequestDto> friendRequestList
                = friendRequestService.findAllFriendRequestByUserId(user.getId());
        return new ResponseEntity<>(friendRequestList, HttpStatus.OK);
    }

    @GetMapping("/request") //requests by other user
    public ResponseEntity<?> getFriendRequest () {
        UserImpl user = userService.getCurrentUser();
        List<FriendRequestDto> friendRequestList
                = friendRequestService.findAllFriendRequestByFriendId(user.getId());
        return new ResponseEntity<>(friendRequestList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeFriend(@PathVariable("id") Long friendId) {
        friendRequestService.removeFriend(friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
