package com.cg_vibely_social_service.entity;

import com.cg_vibely_social_service.utils.PrivacyName;
import com.google.api.client.json.JsonPolymorphicTypeMap;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name ="privacy")
    private PrivacyName privacy;

    @Column(name = "text_content")
    private String textContent;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(columnDefinition = "json",name = "tag")
    private String usersTag;

    private Boolean edited;
}
