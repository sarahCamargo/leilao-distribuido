package com.emiteai.leilao.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class LanceWebSocketHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(LanceWebSocketHandler.class);

    private final List<WebSocketSession> sessoes = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessoes.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessoes.remove(session);
    }

    public void broadcast(String mensagem) {
        for (WebSocketSession sessao : sessoes) {
            try {
                sessao.sendMessage(new TextMessage(mensagem));
            } catch (IOException e) {
                log.error("Falha ao enviar mensagem para a sessao {}", sessao.getId(), e);
            }
        }
    }
}
