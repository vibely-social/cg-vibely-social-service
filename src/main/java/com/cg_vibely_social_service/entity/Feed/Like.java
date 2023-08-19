package com.cg_vibely_social_service.entity.Feed;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    private UUID likeId;
    private Long userId;
    private String date;
}
