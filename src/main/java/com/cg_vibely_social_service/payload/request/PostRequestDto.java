package com.cg_vibely_social_service.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    private Long authorId;
    private String content;
    private String privacy;
    private List<Long> tags;
    private List<Long> subscribers;

}
