package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.payload.request.UserInfoRequestDto;
import com.cg_vibely_social_service.payload.request.UserRegisterRequestDto;
import com.cg_vibely_social_service.payload.response.UserInfoResponseDto;
import com.cg_vibely_social_service.payload.response.UserSearchResponseDto;
import com.cg_vibely_social_service.payload.response.UserSuggestionResponseDto;
import com.cg_vibely_social_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.oauth2.client.OAuth2ClientSecurityMarker;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(
        name = "User",
        description = "API endpoint to get user information"
)
public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(
            summary = "Register new user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User register information",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UserRegisterRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Register new user successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "User information contain error"
                    )
            }
    )
    public ResponseEntity<?> register(@Valid @RequestBody
                                      UserRegisterRequestDto userRegisterRequestDto,
                                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(fieldErrors, HttpStatus.BAD_REQUEST);
        } else {
            try {
                userService.save(userRegisterRequestDto);
            } catch (Exception exception) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/check_email")
    @Operation(
            summary = "Check if email already exist in database",
            parameters = {
                    @Parameter(
                            name = "email",
                            description = "Email for register",
                            in = ParameterIn.QUERY
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Email valid"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Email already exist"
                    )
            }
    )
    public ResponseEntity<?> checkEmail(@RequestParam("email") String email) {
        if (userService.checkValidEmail(email)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    @GetMapping("/{id}/suggestion_friends")
    @Operation(
            summary = "Get suggested friends for user",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Id of the user logged in",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get suggested friend successfully",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = UserSuggestionResponseDto.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Get suggested friend unsuccessfully"
                    )
            }
    )
    public ResponseEntity<?> showSuggestionFriends(@PathVariable("id") Long id) {
        List<UserSuggestionResponseDto> userSuggestion = userService.findFriendSuggestionByUserId(id);
        if (!userSuggestion.isEmpty()){
            return new ResponseEntity<>(userSuggestion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/info/{id}")
    @Operation(
            summary = "Get user information",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of current logged in user",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get user information successfully",
                            content = @Content(
                                    schema = @Schema(implementation = UserInfoResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Get user information unsuccessfully"
                    )
            }
    )
    public ResponseEntity<?> getUserInfo(@PathVariable("id") Long userId) {
        if (userService.findById(userId) != null) {
            UserInfoResponseDto currentUser = userService.getUserInfoById(userId);
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    @Operation(
            summary = "Edit user information",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated information of the user",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UserInfoRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User information updated successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "User information updated unsuccessfully"
                    )
            }
    )
    public ResponseEntity<?> editUserInfo(@Valid @RequestBody
                                          UserInfoRequestDto userInfoRequestDto,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(fieldErrors, HttpStatus.BAD_REQUEST);
        } else {
            try {
                userService.updateUserInfo(userInfoRequestDto);
            } catch (Exception exception) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search for user",
            parameters = {
                    @Parameter(
                            name = "keyword",
                            description = "keyword, in this case is name of user for searching",
                            in = ParameterIn.QUERY
                    ),
                    @Parameter(
                            name = "number-page",
                            description = "Number of the next page",
                            in = ParameterIn.QUERY
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User with keyword in name exist, return a list",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = UserSearchResponseDto.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "There's no user with keyword exist"
                    )
            }
    )
    public ResponseEntity<?> searchUser(@RequestParam("keyword") String keyword,
                                        @RequestParam("number-page") Integer numberPage) {
        List<UserSearchResponseDto> users = userService.findUsersByLastNameOrFirstName(keyword, numberPage);
        if (!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
