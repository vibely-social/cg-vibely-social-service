package com.cg_vibely_social_service.payload.request;

import com.cg_vibely_social_service.entity.Feed.Tag;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewPostRequestDto {
    private Long authorId;
    private String content;
    private String privacy;
    private List<Tag> tags;
}
