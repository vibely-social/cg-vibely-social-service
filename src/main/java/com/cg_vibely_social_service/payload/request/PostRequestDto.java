package com.cg_vibely_social_service.payload.request;
import com.cg_vibely_social_service.utils.PrivacyName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

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

    private List<MultipartFile> files;

}
