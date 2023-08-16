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
public class LoginResponseDto {
    private Long userId;
    private String message;
    private String email;
    private Boolean status;
    private String accessToken;
    private String refreshToken;
}
