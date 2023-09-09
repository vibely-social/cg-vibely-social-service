package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.payload.request.Oauth2RequestDto;
import com.cg_vibely_social_service.payload.request.UserLoginRequestDto;
import com.cg_vibely_social_service.payload.response.UserLoginResponseDto;
import com.cg_vibely_social_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@Tag(
        name = "Authentication",
        description = "API endpoints for authentication"
)
public class AuthController {
    private final UserService userService;

    @PostMapping("/auth/login")
    @Operation(
            summary = "Login using email and password",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Email and Password",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UserLoginRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = UserLoginResponseDto.class)
                            )),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthenticated"
                    )
            }

    )
    public ResponseEntity<UserLoginResponseDto> authentication(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        try {
            UserLoginResponseDto userLoginResponseDto = userService.authenticate(userLoginRequestDto);
            return new ResponseEntity<>(userLoginResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/auth/refresh-token")
    @Operation(
            summary = "Refresh token when access token expire",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "A new access token",
                            content = @Content(
                                    schema = @Schema(type = "string")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401"
                    )
            }
    )
    public ResponseEntity<?> refreshToken() {
        String accessToken = userService.refreshToken();
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }

    @PostMapping("oauth2/google/login")
    @Operation(
            summary = "Login and register using the data return from Google",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Information sent back from Google",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Oauth2RequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = UserLoginResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401"
                    )
            }
    )
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
