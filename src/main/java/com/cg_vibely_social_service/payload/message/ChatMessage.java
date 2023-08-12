package com.cg_vibely_social_service.payload.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String content;
    private String username;
}
