package com.cg_vibely_social_service.entity.Feed;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReplyComment {
    private UUID replyId;
    private Long userId;
    private String content;
    private String gallery;
    private List<Long> likes;
    private String date;
}
