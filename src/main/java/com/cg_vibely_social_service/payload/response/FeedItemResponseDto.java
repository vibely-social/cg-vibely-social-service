package com.cg_vibely_social_service.payload.response;


import com.cg_vibely_social_service.payload.response.UserResponseDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FeedItemResponseDto {

    private Long id;
    private UserResponseDto author;
    private String content;
    private String privacy;
    private List<UserResponseDto> usersTag;
    private List<String> gallery;
    private String createdDate;
    private Long likeCount;
    private Long commentCount;
}
