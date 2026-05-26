# Leilao Distribuido

Trabalho final de **Sistemas Distribuidos**: um sistema de leilao em que
qualquer pessoa pode dar um lance pelo navegador, e todos os outros veem o
lance aparecer na hora.

Por que leilao? Porque o trabalho pede "envio de mensagens". Trocamos
"mensagem" por "lance" pra ficar com cara de aplicacao real. Os conceitos
exigidos pelo PDF (REST, sincrono/assincrono, WebSocket, gRPC, regiao
critica) sao todos demonstrados em cima desse cenario.

---

## Os dois servidores do projeto

Em vez de UM programa, o sistema e formado por DOIS programas que conversam
entre si. Isso e o que o trabalho chama de "sistema distribuido".

### 1. `servidor-web/` (porta 8080)

E o servidor que conversa com o navegador. Pense nele como o
"atendente": recebe pedidos do usuario, processa e responde.

O que ele faz:
- entrega a pagina HTML para o navegador;
- recebe o lance que o usuario envia (REST);
- a partir da Entrega 3, manda atualizacoes em tempo real pro navegador (WebSocket);
- a partir da Entrega 4, pede pro outro servidor guardar o registro do lance (gRPC).

### 2. `servidor-registro/` (sem porta na Entrega 1)

E o servidor "caderninho". Nao fala com o navegador. So existe pra guardar
o historico de cada lance que foi aceito.

Por que separar em dois servidores? **Porque o PDF pede gRPC, e gRPC e
justamente o jeito que dois servidores conversam entre si.** Logo, precisamos
de pelo menos dois.

Na Entrega 1 ele apenas sobe e fica parado (so pra provar que existe). Na
Entrega 4 ele vira um servidor gRPC de verdade na porta 9090.

---

## Tecnologias

| O que | Por que |
|-------|---------|
| **Java 25** + **Spring Boot 3.5** | Stack que a gente ja domina (em vez do Node.js sugerido pelo PDF). |
| **Maven** (multi-modulo) | Um projeto pai com os dois servidores como modulos filhos. |
| **HTML + CSS + JavaScript puro** | Frontend simples, sem framework, pra apresentacao ficar clara. |
| **Spring WebSocket** | Atualizacao em tempo real (substitui Socket.IO sugerido pelo PDF). |
| **gRPC** | Comunicacao entre os dois servidores. Entra na Entrega 4. |

---

## Estrutura de pastas

```
leilao-distribuido/
|
|-- pom.xml                  <- "projeto pai" do Maven
|-- README.md
|
|-- docs/                    <- enunciado original do trabalho
|   `-- trabalho final.pdf
|
|-- frontend/                <- HTML, CSS e JavaScript (a tela do leilao)
|   |-- index.html
|   |-- style.css
|   `-- app.js
|
|-- servidor-web/            <- 1o servidor (fala com o navegador)
|   |-- pom.xml
|   `-- src/main/...
|
`-- servidor-registro/       <- 2o servidor (guarda o historico)
    |-- pom.xml
    `-- src/main/...
```

---

## Como rodar

Precisa ter instalado:
- **JDK 25** (`java -version` precisa mostrar 25)
- **Maven 3.9 ou maior** (`mvn -v`)

Passos:

```bash
# 1. Na pasta raiz, compila os dois servidores
mvn clean install

# 2. Em um terminal, sobe o servidor-web (porta 8080)
cd servidor-web
mvn spring-boot:run

# 3. Em outro terminal, sobe o servidor-registro
cd servidor-registro
mvn spring-boot:run
```

Pronto. Abre `http://localhost:8080` no navegador e clica em
"Testar conexao". Se aparecer um JSON com `status: ok`, a Entrega 1 esta
funcionando.

---

## Roadmap

| Encontro | Data | O que entrega |
|----------|------|---------------|
| 1 | 27/05 | Estrutura inicial + tela basica funcionando |
| 2 | 03/06 | API REST: dar lance e listar lances |
| 3 | 10/06 | Fila assincrona + atualizacao em tempo real (WebSocket) |
| 4 | 17/06 | gRPC entre os dois servidores + protecao de concorrencia |
| 5 | 24/06 | Apresentacao final com tudo junto |

---

## Equipe

- Sarah Camargo
- Alexandre
