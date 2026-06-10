package com.emiteai.leilao.web.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class LanceWebSocketHandler
        extends TextWebSocketHandler {

    private final List<WebSocketSession> sessoes =
            new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(
            WebSocketSession session
    ) {
        sessoes.add(session);
    }

    @Override
    public void afterConnectionClosed(
            WebSocketSession session,
            CloseStatus status
    ) {
        sessoes.remove(session);
    }

    public void broadcast(String mensagem) {

        for (WebSocketSession sessao : sessoes) {

            try {

                sessao.sendMessage(
                        new TextMessage(mensagem)
                );

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}