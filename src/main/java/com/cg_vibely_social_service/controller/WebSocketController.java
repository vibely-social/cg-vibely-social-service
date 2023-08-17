package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.payload.message.ChatMessage;
import com.cg_vibely_social_service.service.UserPrincipal;
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
        Principal user = headerAccessor.getUser();
        String from = user.getName();
        ChatMessage chatMessage = message.getPayload();
        chatMessage.setFrom(from);
        chatMessage.setName(((UserPrincipal)user).getFirstName());
        String sendTo = chatMessage.getSendTo();
        System.out.println(from + ": " + message.getPayload().getContent());
        simpMessagingTemplate.convertAndSendToUser(from, "/queue/messages", chatMessage);
        if (!from.equals(sendTo)) {
            simpMessagingTemplate.convertAndSendToUser(sendTo, "/queue/messages", chatMessage);
        }
    }
}
