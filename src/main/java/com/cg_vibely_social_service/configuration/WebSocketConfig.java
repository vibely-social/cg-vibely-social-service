package com.cg_vibely_social_service.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    @Qualifier("stompWebSocketHandler")
    private WebSocketHandler webSocketHandler;
    @Autowired
    private HandshakeInterceptor handshakeInterceptor;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler,"/ws").addInterceptors(handshakeInterceptor);
    }
}
