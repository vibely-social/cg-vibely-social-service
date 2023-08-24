package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.payload.message.ChatMessageDto;
import com.cg_vibely_social_service.service.ChatService;
import com.cg_vibely_social_service.service.impl.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/ws")
    public void handlePrivateMessage(
            Message<ChatMessageDto> message,
            SimpMessageHeaderAccessor headerAccessor) {
        Principal user = headerAccessor.getUser();
        String sender = user.getName();
        ChatMessageDto chatMessageDto = message.getPayload();
        chatMessageDto.setSender(sender);
        chatMessageDto.setSenderName(((UserPrincipal) user).getFirstName());
        String receiver = chatMessageDto.getReceiver();

        if (!"".equals(chatMessageDto.getContent())) {
            simpMessagingTemplate.convertAndSendToUser(sender, "/queue/messages", chatMessageDto);
            simpMessagingTemplate.convertAndSendToUser(receiver, "/queue/notify", chatMessageDto.getContent());
            if (!sender.equals(receiver)) {
                simpMessagingTemplate.convertAndSendToUser(receiver, "/queue/messages", chatMessageDto);
            }
        }
        chatService.save(chatMessageDto);
    }

    @MessageMapping("/comment/{channelId}")
    public void handlePublicMessage(@DestinationVariable String channelId,
                                    Message<ChatMessageDto> message,
                                    SimpMessageHeaderAccessor headerAccessor) {
        Principal user = headerAccessor.getUser();
        String sender = user.getName();
        System.out.println(channelId);
        System.out.println(message.getHeaders());
        System.out.println(message.getPayload().getContent());
        simpMessagingTemplate.convertAndSend("/topic/post/" + channelId, message);
    }
}
