package com.cg_vibely_social_service.entity.Feed;

import com.cg_vibely_social_service.payload.response.UserResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedItem {
    private Long authorId;
    private String content;
    private String privacy;
    private List<Long> tags;
    private List<String> gallery;
    private String createdDate;
    private List<Long> likes;
    private List<Comment> comments;
}