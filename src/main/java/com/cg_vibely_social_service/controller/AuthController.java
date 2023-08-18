package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.payload.request.UserLoginRequestDto;
import com.cg_vibely_social_service.payload.response.UserLoginResponseDto;
import com.cg_vibely_social_service.configuration.security.JwtUtil;
import com.cg_vibely_social_service.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
//        if (loginResponseDto.isStatus()) {
//            String token = jwtUtil.generateToken(userService.findByEmail(loginRequestDto.getEmail()));
//            Cookie tokenCookie = new Cookie("__vibely",token);
//                tokenCookie.setPath("/");
//                tokenCookie.setHttpOnly(true);
//                tokenCookie.setSecure(true);
//                tokenCookie.setMaxAge(60 * 60);
//                response.addCookie(tokenCookie);
//            return ResponseEntity.ok(loginResponseDto);
        if (userLoginResponseDto.isStatus()) {
            return new ResponseEntity<>(userLoginResponseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/auth/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String bearerToken) {
        String refreshToken = userService.refreshToken(bearerToken);

        if (refreshToken.equals("error")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(refreshToken, HttpStatus.OK);
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
