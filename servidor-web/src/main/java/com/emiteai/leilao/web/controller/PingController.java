package com.emiteai.leilao.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

/**
 * Endpoint de teste da Entrega 1.
 *
 * Serve apenas para o botao "Testar conexao" da tela inicial provar
 * que o navegador consegue falar com o servidor. Nas entregas seguintes
 * sera substituido pelos endpoints de lance.
 */
@RestController
@RequestMapping("/api")
public class PingController {

    @GetMapping("/ping")
    public Map<String, Object> ping() {
        return Map.of(
                "status", "ok",
                "servico", "servidor-web",
                "timestamp", Instant.now().toString()
        );
    }
}
