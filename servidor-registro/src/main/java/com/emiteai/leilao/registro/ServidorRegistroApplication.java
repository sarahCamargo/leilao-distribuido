package com.emiteai.leilao.registro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Servidor de Registro - guarda o historico dos lances.
 *
 * Funciona como um "caderninho" separado do servidor principal:
 *  - recebe cada lance que o Servidor Web aceita
 *  - anota num log (data, valor, quem deu) para manter o historico
 *
 * Por que separar em dois servicos?
 *  Porque o trabalho pede gRPC entre servicos. gRPC e o jeito que dois
 *  servicos conversam entre si. Logo, precisamos de DOIS servicos: o que
 *  fala com o navegador (servidor-web) e o que recebe a chamada gRPC
 *  (este aqui, servidor-registro).
 *
 * Na Entrega 1 ele apenas sobe (so para provar que o segundo servico existe).
 * Na Entrega 4 vai virar um servidor gRPC de verdade.
 */
@SpringBootApplication
public class ServidorRegistroApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServidorRegistroApplication.class, args);
    }
}
