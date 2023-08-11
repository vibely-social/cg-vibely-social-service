package com.vibely_social.payload.request;
import com.vibely_social.entity.PrivacyName;
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
