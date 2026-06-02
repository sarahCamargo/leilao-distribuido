package com.emiteai.leilao.web.model;

import java.math.BigDecimal;
import java.time.Instant;

public record Lance(
        Long id,
        Long leilaoId,
        String usuario,
        BigDecimal valor,
        Instant dataHora
) {
}
