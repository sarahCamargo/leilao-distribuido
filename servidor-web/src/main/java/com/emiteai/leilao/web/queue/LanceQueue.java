package com.emiteai.leilao.web.queue;

import com.emiteai.leilao.web.dto.NovoLanceRequest;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

@Component
public class LanceQueue {

    private final LinkedBlockingQueue<NovoLanceRequest> fila =
            new LinkedBlockingQueue<>();

    public void adicionar(NovoLanceRequest request)
            throws InterruptedException {

        fila.put(request);

        System.out.println("Lance entrou na fila");
    }

    public NovoLanceRequest consumir()
            throws InterruptedException {

        return fila.take();
    }
}