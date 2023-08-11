package com.cg_vibely_social_service.converter.impl;


import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.payload.request.RegisterRequestDto;
import com.cg_vibely_social_service.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserRequestDtoConverter  implements Converter<RegisterRequestDto, User> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public User convert(RegisterRequestDto source) {
        try {
            Date birthday = new SimpleDateFormat("dd/MM/yyyy").parse(source.getDayOfBirth());

            return User.builder()
                    .email(source.getEmail())
                    .password(passwordEncoder.encode(source.getPassword()))
                    .firstName(source.getFirstName())
                    .lastName(source.getLastName())
                    .gender(source.getGender())
                    .dayOfBirth(birthday)
                    .createdAt(new Date())
                    .build();
        } catch (ParseException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public RegisterRequestDto revert(User target) {
        return null;
    }

    @Override
    public List<User> convert(List<RegisterRequestDto> sources) {
        return null;
    }

    @Override
    public List<RegisterRequestDto> revert(List<User> targets) {
        return null;
    }
}
