package com.cg_vibely_social_service.dto.response;

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
public class ResponseLoginDto {
    private String message;
    private boolean status;
    private String email;
    private String accessToken;
    private String refreshToken;
}
