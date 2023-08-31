package com.cg_vibely_social_service.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
