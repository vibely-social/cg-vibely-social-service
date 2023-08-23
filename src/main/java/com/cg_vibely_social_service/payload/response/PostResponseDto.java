package com.cg_vibely_social_service.payload.response;
import com.cg_vibely_social_service.utils.PrivacyName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {

    private Long id;

    private UserResponseDto userInfo;

    private PrivacyName privacy;

    private String textContent;

    private LocalDateTime createdAt;
//    private Boolean edited;

}
