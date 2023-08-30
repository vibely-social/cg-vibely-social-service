package com.cg_vibely_social_service.payload.request;

import lombok.*;

@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentRequestDto {
    private String senderName;
    private Long postId;
    private String content;
}
