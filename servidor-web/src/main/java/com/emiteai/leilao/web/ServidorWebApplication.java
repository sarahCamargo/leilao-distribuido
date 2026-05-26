package com.emiteai.leilao.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Servidor Web - servico principal do sistema.
 *
 * E o servidor que conversa com o navegador.
 *  - serve a pagina HTML (frontend)
 *  - recebe os lances via HTTP (REST)
 *  - manda atualizacao em tempo real pro navegador (WebSocket) - a partir da Entrega 3
 *  - pede pro Servidor de Registro guardar o lance (gRPC) - a partir da Entrega 4
 */
@SpringBootApplication
public class ServidorWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServidorWebApplication.class, args);
    }
}
