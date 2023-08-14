package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.payload.request.UserLoginRequestDto;
import com.cg_vibely_social_service.payload.response.UserLoginResponseDto;
import com.cg_vibely_social_service.configuration.security.JwtUtil;
import com.cg_vibely_social_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/auth/login")
    public ResponseEntity<UserLoginResponseDto> authentication(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        UserLoginResponseDto userLoginResponseDto = userService.authenticate(userLoginRequestDto);

        if (userLoginResponseDto.isStatus()) {
            return ResponseEntity.ok(userLoginResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userLoginResponseDto);
        }
    }

    @GetMapping("/auth/refreshtoken")
    public ResponseEntity<UserLoginResponseDto> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        UserLoginResponseDto userLoginResponseDto = userService.refreshToken(refreshToken);

        if (userLoginResponseDto.isStatus()) {
            return ResponseEntity.ok(userLoginResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(userLoginResponseDto);
        }
    }

    @GetMapping("/admin")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("success");
    }

    @GetMapping("/random")
    public ResponseEntity<?> random() {
        return ResponseEntity.ok("success random");
    }

}
