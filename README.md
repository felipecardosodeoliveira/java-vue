# Java + Vue Project

Projeto de estudo que implementa um backend simples em **Java puro** usando `HttpServer` e um frontend em **Vue.js**.

- **backend/** → Servidor HTTP em Java, com endpoints para gerenciar produtos (`/products`).
- **frontend/** → Interface em Vue.js que consome a API.

---

## Tecnologias

- **Backend**
  - Java 21+
  - [Gson](https://github.com/google/gson) (serialização JSON)
  - JDBC (conexão com banco de dados)
  - Servidor embutido `com.sun.net.httpserver.HttpServer`

- **Frontend**
  - Vue.js 3
  - Fetch API (para consumir a API)
  - Vite

---
