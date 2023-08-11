package com.vibely_social.controller;

import com.vibely_social.payload.request.LoginRequestDto;
import com.vibely_social.payload.response.ResponseLoginDto;
import com.vibely_social.entity.User;
import com.vibely_social.configuration.security.JwtUtil;
import com.vibely_social.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDto> authentication(@RequestBody LoginRequestDto requestLoginDto) {
        ResponseLoginDto failLoginResponse = ResponseLoginDto.builder()
                .message("Invalid credential")
                .status(false)
                .build();

        try {
            User user = (User) userService.loadUserByUsername(requestLoginDto.getEmail());

            if (checkPassword(user, requestLoginDto.getPassword())) {
                String token = jwtUtil.generateToken(user);
                ResponseLoginDto responseLoginDto = ResponseLoginDto.builder()
                        .message("Login successfully")
                        .status(true)
                        .email(user.getEmail())
                        .accessToken(token)
                        .build();
                return ResponseEntity.ok(responseLoginDto);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(failLoginResponse);
            }
        } catch (UsernameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(failLoginResponse);
        }

    }

    @GetMapping("/admin")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("success");
    }

    private boolean checkPassword(User user, String password) {
        return BCrypt.checkpw(password, user.getPassword());
    }
}
