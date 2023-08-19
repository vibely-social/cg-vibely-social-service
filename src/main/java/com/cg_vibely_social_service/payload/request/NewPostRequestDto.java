package com.cg_vibely_social_service.payload.request;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewPostRequestDto {
    private Long authorId;
    private String content;
    private String privacy;
}
