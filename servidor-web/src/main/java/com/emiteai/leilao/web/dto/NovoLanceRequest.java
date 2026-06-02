package com.emiteai.leilao.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record NovoLanceRequest(
        @NotNull(message = "leilaoId e obrigatorio")
        Long leilaoId,

        @NotBlank(message = "usuario e obrigatorio")
        String usuario,

        @NotNull(message = "valor e obrigatorio")
        @Positive(message = "valor deve ser maior que zero")
        BigDecimal valor
) {
}
