package com.cg_vibely_social_service.payload.request;
import com.cg_vibely_social_service.utils.PrivacyName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostRequestDto {

    private Long id;

    private Long userId;

    private PrivacyName privacy;

    private String textContent;

    private LocalDateTime createdAt;

}
