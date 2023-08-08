package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.dto.request.RequestLoginDto;
import com.cg_vibely_social_service.dto.response.ResponseLoginDto;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.security.JwtUtil;
import com.cg_vibely_social_service.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/authentication")
    public ResponseEntity<ResponseLoginDto> authentication(@RequestBody RequestLoginDto requestLoginDto) {
        try {
            User user = (User) userService.loadUserByUsername(requestLoginDto.getEmail());

            if (checkPassword(user, requestLoginDto.getPassword())) {
                String token = jwtUtil.generateToken(user);
                ResponseLoginDto responseLoginDto = new ResponseLoginDto(user.getEmail(), token);
                return ResponseEntity.ok(responseLoginDto);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (UsernameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @GetMapping("/random")
    public void test() {

    }

    private boolean checkPassword(User user, String password) {
        return BCrypt.checkpw(password, user.getPassword());
    }
}
