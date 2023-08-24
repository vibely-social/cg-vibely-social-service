package com.cg_vibely_social_service.converter.impl;


import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.UserRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserRequestDtoConverter  implements Converter<UserRegisterRequestDto, User> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public User convert(UserRegisterRequestDto source) {
        //            Date birthday = new SimpleDateFormat("dd/MM/yyyy").parse(source.getDayOfBirth());
        LocalDate birthday = LocalDate.parse(source.getDayOfBirth(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return User.builder()
                .email(source.getEmail())
                .password(passwordEncoder.encode(source.getPassword()))
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .gender(source.getGender())
                .dayOfBirth(birthday)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    public UserRegisterRequestDto revert(User target) {
        return null;
    }

    @Override
    public List<User> convert(List<UserRegisterRequestDto> sources) {
        return null;
    }

    @Override
    public List<UserRegisterRequestDto> revert(List<User> targets) {
        return null;
    }
}
