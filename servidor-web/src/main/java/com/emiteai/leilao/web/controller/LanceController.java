package com.emiteai.leilao.web.controller;

import com.emiteai.leilao.web.dto.NovoLanceRequest;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lances")
public class LanceController {

    private final LanceRepository repository;

    public LanceController(LanceRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Lance> listar(@RequestParam(name = "leilaoId", required = false) Long leilaoId) {
        return Optional.ofNullable(leilaoId)
                .map(repository::listarPorLeilao)
                .orElseGet(repository::listarTodos);
    }

    @PostMapping
    public ResponseEntity<Lance> criar(@Valid @RequestBody NovoLanceRequest request,
                                       UriComponentsBuilder uriBuilder) {
        Lance lance = repository.salvar(request.leilaoId(), request.usuario(), request.valor());

        URI location = uriBuilder.path("/api/lances/{id}")
                .buildAndExpand(lance.id())
                .toUri();

        return ResponseEntity.created(location).body(lance);
    }
}
