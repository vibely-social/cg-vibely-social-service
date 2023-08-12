package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.payload.message.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{channelId}")
    public void handleMessage(@DestinationVariable String channelId, Message<ChatMessage> message) {
        System.out.println(channelId);
        System.out.println(message.getHeaders());
        System.out.println(message.getPayload().getUsername() + ": " + message.getPayload().getContent());
        simpMessagingTemplate.convertAndSend("/topic/listen/" + channelId, message.getPayload());
    }
}
