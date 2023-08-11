package com.cg_vibely_social_service.payload.request;
import com.cg_vibely_social_service.utils.PrivacyName;
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

public class PostRequestDto {

    private Long id;

    private Long userId;

    private PrivacyName privacy;

    private String textContent;

    private LocalDateTime dateTime;

}
