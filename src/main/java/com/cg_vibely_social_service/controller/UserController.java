package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.converter.impl.UserRequestDtoConverter;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.RegisterRequestDto;
import com.cg_vibely_social_service.payload.response.UserSuggestionResponseDto;
import com.cg_vibely_social_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Converter<RegisterRequestDto, User> converter;

    @PostMapping()
    public ResponseEntity<?> register(@Valid @RequestBody
                                      RegisterRequestDto registerRequestDto,
                                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try {
                userService.save(converter.convert(registerRequestDto));
            } catch (Exception exception) {
//                return new ResponseEntity(HttpStatus.BAD_REQUEST);
                return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
            }
            return  ResponseEntity.status(HttpStatus.OK).body("Ok");

//            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> checkEmail(@PathVariable("email") String email) {
        if (userService.checkValidEmail(email)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}/suggestionFriends")
    public ResponseEntity<?> showSuggestionFriends(@PathVariable("id") Long id) {
        List<UserSuggestionResponseDto> userSuggestion = userService.find20UsersSuggestionByUserId(id);
        if (!userSuggestion.isEmpty()){
            return new ResponseEntity<>(userSuggestion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
