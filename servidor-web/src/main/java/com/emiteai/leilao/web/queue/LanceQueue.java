package com.emiteai.leilao.web.queue;

import com.emiteai.leilao.web.dto.NovoLanceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

@Component
public class LanceQueue {

    private static final Logger log = LoggerFactory.getLogger(LanceQueue.class);

    private final LinkedBlockingQueue<NovoLanceRequest> fila = new LinkedBlockingQueue<>();

    public void adicionar(NovoLanceRequest request) throws InterruptedException {
        fila.put(request);
        log.info("Lance entrou na fila");
    }

    public NovoLanceRequest consumir() throws InterruptedException {
        return fila.take();
    }
}
