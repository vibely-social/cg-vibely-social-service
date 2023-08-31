package com.cg_vibely_social_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "friend_request")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long friendId;

}
