package com.emiteai.leilao.web.controller;

import com.emiteai.leilao.web.dto.NovoLanceRequest;
import com.emiteai.leilao.web.exception.LanceInvalidoException;
import com.emiteai.leilao.web.model.Lance;
import com.emiteai.leilao.web.repository.LanceRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.emiteai.leilao.web.queue.LanceQueue;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lances")
public class LanceController {

    private final LanceRepository repository;
    private final LanceQueue queue;

    public LanceController(
            LanceRepository repository,
            LanceQueue queue
    ) {
        this.repository = repository;
        this.queue = queue;
    }

    @GetMapping
    public List<Lance> listar(@RequestParam(name = "leilaoId", required = false) Long leilaoId) {
        return Optional.ofNullable(leilaoId)
                .map(repository::listarPorLeilao)
                .orElseGet(repository::listarTodos);
    }

    @PostMapping
    public ResponseEntity<String> criar(
            @Valid @RequestBody NovoLanceRequest request
    ) throws InterruptedException {

        repository.maiorValorDoLeilao(request.leilaoId())
                .filter(maiorAtual -> request.valor().compareTo(maiorAtual) <= 0)
                .ifPresent(maiorAtual -> {
                    throw new LanceInvalidoException(
                            "Lance de R$ " + request.valor() + " nao supera o maior lance atual do leilao "
                                    + request.leilaoId() + " (R$ " + maiorAtual + ")");
                });

        queue.adicionar(request);

        return ResponseEntity
                .accepted()
                .body("Lance enviado para fila");
    }
}
