package com.emiteai.leilao.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig
        implements WebSocketConfigurer {

    private final LanceWebSocketHandler handler;

    public WebSocketConfig(
            LanceWebSocketHandler handler
    ) {
        this.handler = handler;
    }

    @Override
    public void registerWebSocketHandlers(
            WebSocketHandlerRegistry registry
    ) {

        registry.addHandler(
                (WebSocketHandler) handler,
                "/ws/lances"
        ).setAllowedOrigins("*");
    }
}