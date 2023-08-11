package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.payload.request.LoginRequestDto;
import com.cg_vibely_social_service.payload.response.LoginResponseDto;
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
    public ResponseEntity<LoginResponseDto> authentication(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = userService.authenticate(loginRequestDto);

        if (loginResponseDto.isStatus()) {
            return ResponseEntity.ok(loginResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponseDto);
        }
    }

    @GetMapping("/auth/refreshtoken")
    public ResponseEntity<LoginResponseDto> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        LoginResponseDto loginResponseDto = userService.refreshToken(refreshToken);

        if (loginResponseDto.isStatus()) {
            return ResponseEntity.ok(loginResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(loginResponseDto);
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
