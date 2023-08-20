package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.converter.impl.PostRequestDtoConverter;
import com.cg_vibely_social_service.entity.Feed.FeedItem;
import com.cg_vibely_social_service.service.FeedService;
import com.cg_vibely_social_service.service.ImageService;
import com.cg_vibely_social_service.service.PostService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;
    private final ImageService imageService;
    @PostMapping("/add")
    public ResponseEntity<?> submitPost(@RequestParam(value = "files",required = false) List<MultipartFile> files,
                                        @RequestParam(value = "newPostDTO") String newPostDTO){
        try {
            if(files != null) {
                List<String> fileNames = imageService.save(files);
                feedService.newPost(newPostDTO, fileNames);
            }
            else{
                if(newPostDTO != null) feedService.newPost(newPostDTO);
                else{
                    return new ResponseEntity<>("Can't create empty post",HttpStatus.NOT_ACCEPTABLE);
                }
            }
            return new ResponseEntity<>("Your post was created!",HttpStatus.CREATED);
//            return new ResponseEntity<>(imageService.getImageUrls(fileNames), HttpStatus.CREATED);
        }
        catch (JsonMappingException e){
            return new ResponseEntity<>("Invalid new post data",HttpStatus.NOT_ACCEPTABLE);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.PAYMENT_REQUIRED);
        }
    }
}
