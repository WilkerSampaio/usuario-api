# 🚀 Usuário API

API para gerenciamento de usuários com autenticação e autorização via JWT. Possui integração com ViaCep para busca de dados de endereço a partir do CEP.

---

## 🛠 Tecnologias Utilizadas

- ☕ **Java 17**  
- 🌱 **Spring Boot**  
- 📦 **Gradle**  
- 🐘 **PostgreSQL**  
- 🐳 **Docker**  
- 📄 **Swagger (OpenAPI)**  
- 🔐 **JWT** para autenticação e autorização  

---

## ✨ Funcionalidades

- 📝 Registrar novos usuários  
- 🔑 Autenticar usuários (login)  
- 🔍 Buscar usuário pelo email  
- 🔄 Atualizar dados do usuário, endereço e telefone  
- ➕ Cadastrar endereço e telefone  
- ❌ Deletar usuário pelo email  
- 🏠 Buscar dados de endereço via integração com ViaCep  

---

## 📡 Endpoints Principais

| Método  | Rota                  | Descrição                              | Autenticação |
|---------|-----------------------|----------------------------------------|--------------|
| POST    | `/`                   | Registrar novo usuário                 | ❌           |
| POST    | `/login`              | Autenticar usuário (retorna token)     | ❌           |
| GET     | `/`                   | Buscar usuário pelo email              | ✅           |
| DELETE  | `/{email}`            | Deletar usuário pelo email             | ✅           |
| PUT     | `/`                   | Atualizar dados do usuário             | ✅           |
| PUT     | `/endereco`           | Atualizar endereço do usuário          | ✅           |
| PUT     | `/telefone`           | Atualizar telefone do usuário          | ✅           |
| POST    | `/endereco`           | Cadastrar novo endereço para usuário   | ✅           |
| POST    | `/telefone`           | Cadastrar novo telefone para usuário   | ✅           |
| GET     | `/endereco/{cep}`     | Buscar dados de endereço pelo CEP      | ❌           |

## 🚀 Uso com Docker Compose (modo simples)

Este projeto já está preparado para ser usado apenas com Docker Compose — não é necessário compilar localmente, executar comandos Maven/Gradle, etc.  

### Pré-requisitos

- Docker  
- Docker Compose  

### Passos para subir a aplicação

1. Clone o repositório:  
   ```bash
   git clone https://github.com/WilkerSampaio/usuario-api.git
   cd usuario-api
   ```

2. Ajuste as variáveis de ambiente no `docker-compose.yml` (ou use um arquivo `.env` para deixar segredos fora do controle de versão).  

   Dentro do `docker-compose.yml`, você verá algo como:

   ```yaml
   services:
     app:
       build: .
       ports:
         - "8080:8080"
       environment:
         SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/banco
         SPRING_DATASOURCE_USERNAME: <seu_usuario>
         SPRING_DATASOURCE_PASSWORD: <sua_senha>
       depends_on:
         - db

     db:
       image: postgres:latest
       environment:
         POSTGRES_DB: banco
         POSTGRES_USER: <seu_usuario>
         POSTGRES_PASSWORD: <sua_senha>
       ports:
         - "5432:5432"
   ```

   Você precisa substituir `<seu_usuario>` e `<sua_senha>` pelas credenciais que desejar usar.

3. Suba os containers com o Docker Compose:

   ```bash
   docker-compose up --build
   ```

4. A aplicação estará rodando em:

   ```
   http://localhost:8080
   ```

5. Acesse o Swagger para explorar os endpoints:

   👉 [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)  

---
