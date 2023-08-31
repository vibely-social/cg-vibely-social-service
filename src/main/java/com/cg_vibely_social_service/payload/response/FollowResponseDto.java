package com.cg_vibely_social_service.payload.response;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FollowResponseDto {
    private Long id;
    private Long userId;
    private Long followedUserId;
    private String name;
    private String avatarUrl;
//    private Integer MutualFriend;
}
