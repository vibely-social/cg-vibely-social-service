package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.payload.message.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ws")
    public void handleMessage(
            Message<ChatMessage> message,
            SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        Principal user = headerAccessor.getUser();
        System.out.println(user.getName());
        System.out.println(message.getPayload().getUsername() + ": " + message.getPayload().getContent());
//        simpMessagingTemplate.convertAndSend("/queue/messages", message.getPayload());
        simpMessagingTemplate.convertAndSendToUser("Lennie", "/queue/messages", message.getPayload());
        simpMessagingTemplate.convertAndSendToUser("Rachel", "/queue/messages", message.getPayload());
//        simpMessagingTemplate.getMessageChannel().send(message);
    }
}
