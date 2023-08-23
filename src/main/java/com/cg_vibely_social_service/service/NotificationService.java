package com.cg_vibely_social_service.service;

import com.cg_vibely_social_service.payload.message.ChatMessageDto;

public interface NotificationService {
    void sendNotify (String userEmail, ChatMessageDto chatMessageDto);
}
