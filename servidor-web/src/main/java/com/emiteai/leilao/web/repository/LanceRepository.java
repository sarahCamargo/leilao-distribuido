package com.emiteai.leilao.web.repository;

import com.emiteai.leilao.web.exception.LanceInvalidoException;
import com.emiteai.leilao.web.model.Lance;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class LanceRepository {

    private final Map<Long, Lance> lances = new ConcurrentHashMap<>();
    private final AtomicLong sequencia = new AtomicLong(0);

    public Lance salvar(Long leilaoId, String usuario, BigDecimal valor) {
        maiorValorDoLeilao(leilaoId).ifPresent(maiorAtual -> {
            if (valor.compareTo(maiorAtual) <= 0) {
                throw new LanceInvalidoException(
                        "Lance de R$ " + valor + " nao supera o maior lance atual do leilao "
                                + leilaoId + " (R$ " + maiorAtual + ")");
            }
        });

        long id = sequencia.incrementAndGet();
        Lance lance = new Lance(id, leilaoId, usuario, valor, Instant.now());
        lances.put(id, lance);
        return lance;
    }

    public Optional<BigDecimal> maiorValorDoLeilao(Long leilaoId) {
        return lances.values().stream()
                .filter(lance -> lance.leilaoId().equals(leilaoId))
                .map(Lance::valor)
                .max(BigDecimal::compareTo);
    }

    public List<Lance> listarTodos() {
        return List.copyOf(lances.values());
    }

    public List<Lance> listarPorLeilao(Long leilaoId) {
        return lances.values().stream()
                .filter(lance -> lance.leilaoId().equals(leilaoId))
                .toList();
    }

    public Optional<Lance> buscarPorId(Long id) {
        return Optional.ofNullable(lances.get(id));
    }
}
