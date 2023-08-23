package com.cg_vibely_social_service.entity.Feed;

import lombok.*;

import java.util.List;
@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedItem {

    private Long authorId;
    private String content;
    private String privacy;
    private List<Long> tags;
    private List<String> gallery;
    private String createdDate;
    private List<Long> likes;
    private List<Comment> comments;
    private List<Long> subscribers;
}
