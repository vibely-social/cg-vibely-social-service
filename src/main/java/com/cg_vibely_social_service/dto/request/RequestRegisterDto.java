package com.cg_vibely_social_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RequestRegisterDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String dayOfBirth;
}
