package com.cg_vibely_social_service.dto.post.response;

import com.cg_vibely_social_service.dto.user.response.UserDtoResponse;
import com.cg_vibely_social_service.entity.PrivacyName;
import com.cg_vibely_social_service.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDtoResponse {
    private Long id;

    private UserDtoResponse userDtoResponse;

    private PrivacyName privacy;

    private String textContent;

    private LocalDateTime dateTime;

}
