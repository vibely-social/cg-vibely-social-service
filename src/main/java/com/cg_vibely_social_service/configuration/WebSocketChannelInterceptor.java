package com.cg_vibely_social_service.configuration;

import com.cg_vibely_social_service.configuration.security.JwtUtil;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.service.UserPrincipal;
import com.cg_vibely_social_service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebSocketChannelInterceptor implements ChannelInterceptor {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final HttpServletRequest request;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            Object rawHeaders = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);

            if (rawHeaders instanceof Map) {
                Object name = ((Map) rawHeaders).get("email");

                if (name instanceof ArrayList) {
                    accessor.setUser(new UserPrincipal(((ArrayList<String>) name).get(0)));
                }
            }
        }
        return message;
    }




//        try {
//            MessageHeaders messageHeaders = message.getHeaders();
//            String stompCMD = messageHeaders.get("stompCommand").toString();
//            StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//            List<String> tokenList = accessor.getNativeHeader("Authorization");
//            String jwtToken = tokenList.get(0).substring(7);
//            String email = jwtUtil.extractEmail(jwtToken);
//            UserDetails userDetails = userService.loadUserByUsername(email);
//            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            accessor.setUser(authentication);
//
//            if (!stompCMD.equals("CONNECT")) return message;
//            LinkedMultiValueMap<String, String> headers = (LinkedMultiValueMap<String, String>) messageHeaders.get("nativeHeaders");
//            if (headers!=null){
//                if (jwtToken!=null){
//                    if (userDetails != null && jwtUtil.isTokenValid(jwtToken)) {
//                        System.out.println(userDetails.getUsername());
//                        return message;
//                    }
//                }
//            }
//            return null;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
