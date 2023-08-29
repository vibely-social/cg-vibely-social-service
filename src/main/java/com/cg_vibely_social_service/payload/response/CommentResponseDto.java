package com.cg_vibely_social_service.payload.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private String gallery;
    private UserResponseDto author;
    private String date;
    private List<CommentResponseDto> replyCommentDTOs;
}
