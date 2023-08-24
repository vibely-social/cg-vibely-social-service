package com.cg_vibely_social_service.configuration;

import com.cg_vibely_social_service.configuration.security.JwtTokenProvider;
import com.cg_vibely_social_service.service.impl.UserPrincipal;
import com.cg_vibely_social_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebSocketChannelInterceptor implements ChannelInterceptor {
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (headerAccessor != null && StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
            Object rawHeaders = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
            if (rawHeaders instanceof Map) {
                Object authorHeader = ((Map<?, ?>) rawHeaders).get("Authorization");
                if (authorHeader instanceof ArrayList) {
                    String bearerToken = ((ArrayList<String>) authorHeader).get(0);
                    if (tokenProvider.isTokenValid(bearerToken)) {
                        String email = tokenProvider.extractEmail(bearerToken);
                        if (email != null) {
                            UserPrincipal userPrincipal;
                            userPrincipal = userService.getUserPrincipal(email);
                            System.out.println("calling db at interceptor");
                            headerAccessor.setUser(userPrincipal);
                            return message;
                        } else {
                            return null;
                        }
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        if (headerAccessor != null && StompCommand.DISCONNECT.equals(headerAccessor.getCommand())) {
            return null;
        }
        if (headerAccessor != null && StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
            if (headerAccessor.getUser() == null || headerAccessor.getUser().getName() == null){
                return null;
            }
            System.out.println(headerAccessor.getDestination());
            System.out.println("Some one SUBSCRIBED");
        }
        headerAccessor.setHeartbeat(5000,5000);
        return message;
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (headerAccessor != null && StompCommand.SEND.equals(headerAccessor.getCommand())) {
            if (sent) {
//                Object fromMessage = messageConverter.fromMessage(message, ChatMessageDto.class);
                System.out.println("sent");
            } else {
                System.err.println("sent failed");
            }
        }
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        System.out.println(message.getPayload());
    }
}
