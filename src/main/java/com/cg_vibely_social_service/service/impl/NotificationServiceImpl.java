package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.payload.message.ChatMessageDto;
import com.cg_vibely_social_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    @Override
    public void sendNotify(String userEmail, ChatMessageDto chatMessageDto) {
        simpMessagingTemplate.convertAndSendToUser(userEmail, "/queue/notify", chatMessageDto);
    }
}
