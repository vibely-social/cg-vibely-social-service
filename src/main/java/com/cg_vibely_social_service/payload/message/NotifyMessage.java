package com.cg_vibely_social_service.payload.message;

import java.time.LocalDateTime;

public class NotifyMessage {
    private NotificationType type;
    private String message;
    private LocalDateTime time = LocalDateTime.now();

}
