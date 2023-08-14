package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.entity.FakePost;
import com.cg_vibely_social_service.service.FakePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//This controller is for testing only
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class FakePostController {
    private final FakePostService fakePostService;

    @GetMapping("/mediatab/{id}")
    public ResponseEntity<List<FakePost>> getMediasForMediaTab(@PathVariable("id") Long userId) {
        List<FakePost> fakePostList = fakePostService.getFirst9ForMediaTabByUserId(userId);
        return ResponseEntity.ok(fakePostList);
    }

    @PostMapping("/posts")
    public ResponseEntity<?> testPosts() {
        return ResponseEntity.ok("Test for post");
    }

}
