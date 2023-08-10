package com.cg_vibely_social_service.dto.converter;


import com.cg_vibely_social_service.dto.request.RequestRegisterDto;
import com.cg_vibely_social_service.entity.Gender;
import com.cg_vibely_social_service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UserConverter {
    @Value("${date.pattern}")
    private String datePattern;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerDtoToUser(RequestRegisterDto requestRegisterDto) {
        try {
            Date birthday = new SimpleDateFormat(datePattern).parse(requestRegisterDto.getDayOfBirth());

            return User.builder()
                    .email(requestRegisterDto.getEmail())
                    .password(passwordEncoder.encode(requestRegisterDto.getPassword()))
                    .firstName(requestRegisterDto.getFirstName())
                    .lastName(requestRegisterDto.getLastName())
                    .gender(Gender.valueOf(requestRegisterDto.getGender()))
                    .dayOfBirth(birthday)
                    .createdAt(new Date())
                    .build();
        } catch (ParseException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
