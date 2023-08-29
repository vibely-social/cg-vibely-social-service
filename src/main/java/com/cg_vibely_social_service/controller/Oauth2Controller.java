package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.payload.request.Oauth2RequestDto;
import com.cg_vibely_social_service.payload.request.UserLoginRequestDto;
import com.cg_vibely_social_service.payload.response.UserLoginResponseDto;
import com.cg_vibely_social_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth2")
@RequiredArgsConstructor
public class Oauth2Controller {
    private final UserService userService;

    @PostMapping ("/google/login")
    public ResponseEntity<?> oauth2Login(@RequestBody Oauth2RequestDto oauth2RequestDto) {
        try {
            if (userService.checkValidEmail(oauth2RequestDto.getEmail())) {
                userService.save(oauth2RequestDto);
            }
            UserLoginRequestDto userLoginRequestDto = UserLoginRequestDto.builder()
                    .email(oauth2RequestDto.getEmail())
                    .password(oauth2RequestDto.getPassword())
                    .build();
            UserLoginResponseDto userLoginResponseDto = userService.authenticate(userLoginRequestDto);
            return new ResponseEntity<>(userLoginResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
