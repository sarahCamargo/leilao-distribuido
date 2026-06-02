package com.emiteai.leilao.web.controller;

import com.emiteai.leilao.web.exception.LanceInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarValidacao(MethodArgumentNotValidException ex) {
        List<String> erros = ex.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .toList();

        Map<String, Object> corpo = Map.of(
                "status", HttpStatus.BAD_REQUEST.value(),
                "erro", "Requisicao invalida",
                "detalhes", erros,
                "timestamp", Instant.now().toString()
        );

        return ResponseEntity.badRequest().body(corpo);
    }

    @ExceptionHandler(LanceInvalidoException.class)
    public ResponseEntity<Map<String, Object>> tratarLanceInvalido(LanceInvalidoException ex) {
        Map<String, Object> corpo = Map.of(
                "status", HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "erro", "Lance recusado",
                "detalhes", List.of(ex.getMessage()),
                "timestamp", Instant.now().toString()
        );

        return ResponseEntity.unprocessableEntity().body(corpo);
    }
}
