package com.cg_vibely_social_service.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class FollowRequestDto {
    private Long userId;
    private Long targetId;
}
