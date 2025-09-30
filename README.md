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
