# Frontend foi movido

Os arquivos HTML/CSS/JS ficavam aqui (convencao do Spring Boot), mas a gente
mudou pra pasta `frontend/` na raiz do projeto - mais facil de achar e editar.

O servidor-web esta configurado em `application.yml` para servir os arquivos
da pasta `../frontend/` quando voce roda `mvn spring-boot:run`.

**Pode deletar esta pasta `static/` se quiser, nao quebra nada.**
