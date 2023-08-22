package com.cg_vibely_social_service.entity.Feed;

import lombok.*;

import java.util.UUID;

@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    private UUID likeId;
    private Long userId;
    private String date;
}
