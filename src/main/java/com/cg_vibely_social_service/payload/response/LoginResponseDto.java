<<<<<<<< HEAD:src/main/java/com/vibely_social/payload/response/ResponseLoginDto.java
package com.vibely_social.payload.response;
========
package com.cg_vibely_social_service.payload.response;
>>>>>>>> 1261f2bf1f1141a807a673cb59e87e47420e3b50:src/main/java/com/cg_vibely_social_service/payload/response/LoginResponseDto.java

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
    private String message;
    private boolean status;
    private String email;
    private String accessToken;
    private String refreshToken;
}
