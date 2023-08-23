package com.cg_vibely_social_service.controller;


import com.cg_vibely_social_service.payload.response.FriendResponseDto;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getFriendList (@PathVariable("id") Long id ) {
        UserImpl user = userService.getCurrentUser();
        if (user != null && user.getId().equals(id)) {
            List<FriendResponseDto> friendList = friendService.findFriendsByUserId(id);
            return new ResponseEntity<>(friendList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
