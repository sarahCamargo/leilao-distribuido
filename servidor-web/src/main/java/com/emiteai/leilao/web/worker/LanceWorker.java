package com.emiteai.leilao.web.worker;

import com.emiteai.leilao.web.config.LanceWebSocketHandler;
import com.emiteai.leilao.web.dto.NovoLanceRequest;
import com.emiteai.leilao.web.model.Lance;
import com.emiteai.leilao.web.queue.LanceQueue;
import com.emiteai.leilao.web.repository.LanceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class LanceWorker {

    private final LanceQueue queue;
    private final LanceRepository repository;
    private final LanceWebSocketHandler socketHandler;

    private final ObjectMapper mapper =
            new ObjectMapper();

    public LanceWorker(
            LanceQueue queue,
            LanceRepository repository,
            LanceWebSocketHandler socketHandler
    ) {
        this.queue = queue;
        this.repository = repository;
        this.socketHandler = socketHandler;
    }

    @PostConstruct
    public void iniciar() {

        Thread thread = new Thread(() -> {

            while (true) {

                try {

                    NovoLanceRequest request =
                            queue.consumir();

                    System.out.println(
                            "Processando lance..."
                    );

                    Lance lance =
                            repository.salvar(
                                    request.leilaoId(),
                                    request.usuario(),
                                    request.valor()
                            );

                    socketHandler.broadcast(
                            mapper.writeValueAsString(lance)
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }
}