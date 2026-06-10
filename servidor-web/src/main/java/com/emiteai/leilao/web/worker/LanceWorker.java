package com.emiteai.leilao.web.worker;

import com.emiteai.leilao.web.config.LanceWebSocketHandler;
import com.emiteai.leilao.web.dto.NovoLanceRequest;
import com.emiteai.leilao.web.model.Lance;
import com.emiteai.leilao.web.queue.LanceQueue;
import com.emiteai.leilao.web.repository.LanceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LanceWorker {

    private static final Logger log = LoggerFactory.getLogger(LanceWorker.class);

    private final LanceQueue queue;
    private final LanceRepository repository;
    private final LanceWebSocketHandler socketHandler;
    private final ObjectMapper mapper;

    public LanceWorker(LanceQueue queue,
                       LanceRepository repository,
                       LanceWebSocketHandler socketHandler,
                       ObjectMapper mapper) {
        this.queue = queue;
        this.repository = repository;
        this.socketHandler = socketHandler;
        this.mapper = mapper;
    }

    @PostConstruct
    public void iniciar() {
        Thread thread = new Thread(this::processarFila, "lance-worker");
        thread.setDaemon(true);
        thread.start();
    }

    private void processarFila() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                processarProximo();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                log.error("Erro ao processar lance da fila", e);
            }
        }
    }

    private void processarProximo() throws InterruptedException, JsonProcessingException {
        NovoLanceRequest request = queue.consumir();
        log.info("Processando lance...");

        Thread.sleep(500);

        Lance lance = repository.salvar(request.leilaoId(), request.usuario(), request.valor());
        socketHandler.broadcast(mapper.writeValueAsString(lance));
    }
}
