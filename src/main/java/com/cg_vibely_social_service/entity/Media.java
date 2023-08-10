package com.cg_vibely_social_service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user")
    private Long userID;
    @Column(name ="post_id")
    private Long postID;
    @Column(name = "filename")
    private String fileName;
    @Column(name = "type")
    private String type;
    @Column(name ="created_at")
    private LocalDateTime createdDate;
}
