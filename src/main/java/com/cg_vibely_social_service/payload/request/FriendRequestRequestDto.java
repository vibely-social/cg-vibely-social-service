package com.cg_vibely_social_service.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FriendRequestRequestDto {
    private Long id;
    private Long userId;
    private Long friendId;
    private String name;
    private String avatarUrl;
}
