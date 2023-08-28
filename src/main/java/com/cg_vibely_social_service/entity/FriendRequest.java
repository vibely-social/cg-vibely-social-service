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

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(referencedColumnName = "id",nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "friend_id")
    private User friend;

}
