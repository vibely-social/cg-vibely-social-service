<<<<<<<< HEAD:src/main/java/com/vibely_social/payload/request/LoginRequestDto.java
package com.vibely_social.payload.request;
========
package com.cg_vibely_social_service.payload.request;
>>>>>>>> 1261f2bf1f1141a807a673cb59e87e47420e3b50:src/main/java/com/cg_vibely_social_service/payload/request/LoginRequestDto.java

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
public class LoginRequestDto {
    private String email;
    private String password;
}
