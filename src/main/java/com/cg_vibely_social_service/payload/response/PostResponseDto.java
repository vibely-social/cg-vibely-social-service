package com.cg_vibely_social_service.payload.response;
import com.cg_vibely_social_service.utils.PrivacyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private Long id;
    private UserResponseDto author;
    private String content;
    private String privacy;
    private List<UserResponseDto> usersTag;
    private List<String> gallery;
    private String createdDate;
    private List<Long> like;
    private Long commentCount;

}
