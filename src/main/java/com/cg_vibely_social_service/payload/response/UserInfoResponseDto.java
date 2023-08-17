package com.cg_vibely_social_service.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserInfoResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String birthday;
    private String gender;
    private String phoneNumber;
    private String city;
}
