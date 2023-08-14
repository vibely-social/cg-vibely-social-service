package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.response.FriendInfoResponse;
import com.cg_vibely_social_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<FriendInfoResponse>> getFriends() {
        List<User> userList =  userService.findAll();
        List<FriendInfoResponse> friendInfoResponses = new ArrayList<>();

        for (User user : userList) {
            FriendInfoResponse friendInfoResponse = new FriendInfoResponse();
            friendInfoResponse.setEmail(user.getEmail());
            friendInfoResponses.add(friendInfoResponse);
        }

        return ResponseEntity.ok(friendInfoResponses);
    }
}
