package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.dto.request.RequestRegisterDto;
import com.cg_vibely_social_service.dto.response.ResponseRegisterDto;
import com.cg_vibely_social_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<ResponseRegisterDto> register(@RequestBody RequestRegisterDto requestRegisterDto) {
        ResponseRegisterDto responseRegisterDto = userService.register(requestRegisterDto);

        if (responseRegisterDto.isStatus()) {
            return ResponseEntity.ok(responseRegisterDto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseRegisterDto);
        }
    }
}
