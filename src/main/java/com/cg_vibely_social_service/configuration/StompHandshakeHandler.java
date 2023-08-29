package com.cg_vibely_social_service.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
@Configuration
@Qualifier("stompHandshakeHandler")
public class StompHandshakeHandler extends DefaultHandshakeHandler {

}
