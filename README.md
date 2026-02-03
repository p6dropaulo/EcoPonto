# ♻️ EcoPonto

O EcoPonto é um sistema de gestão para coleta seletiva que conecta doadores de materiais recicláveis a associações de reciclagem. O objetivo é facilitar o descarte correto de resíduos e fortalecer as associações locais.

🎓 Este projeto foi desenvolvido como exercício acadêmico da disciplina de Desenvolvimento Back-end, com o intuito de aplicar conceitos de arquitetura de software, segurança e persistência de dados.

---

## 📌 Objetivo do Projeto

O EcoPonto facilita a doação de materiais recicláveis, permitindo que:

- ♻️ **Doadores** registrem itens disponíveis para coleta  
- 🏭 **Associações** visualizem e coletem materiais  

---

## 🛠️ Tecnologias Utilizadas

| Categoria        | Tecnologia                                          |
|------------------|-----------------------------------------------------|
| Linguagem        | Java 17                                             |
| Framework        | Spring Boot 3+ (Data JPA, Security, Validation)     |
| Segurança        | Spring Security + JWT (JSON Web Token)              |
| Banco de Dados   | H2 Database (Desenvolvimento) / PostgreSQL (Produção) |
| Documentação     | Swagger (OpenAPI 3)                                 |
| Containerização  | Docker & Docker Compose                             |

---

## 📁 Estrutura do Projeto

Abaixo, a organização dos principais pacotes do sistema:

```
EcoPonto/
├── src/main/java/com/backend/ecoponto/
│   ├── controller/   # Endpoints (Auth, Itens, Associações)
│   ├── service/      # Regras de negócio e UserDetails
│   ├── security/     # Filtros JWT e Configurações de Segurança
│   ├── model/        # Entidades JPA (Usuario, Item, etc)
│   ├── repository/   # Interfaces de acesso ao banco
│   ├── dto/          # Objetos de transferência de dados (Create, Response, Login)
│   ├── mapper/       # Conversão entre Entidades e DTOs
│   └── exception/    # Manipulação global de erros
├── src/main/resources/
│   ├── application-DEV.properties  # Configuração banco H2
│   └── application-PRD.properties  # Configuração banco PostgreSQL
├── Dockerfile                      # Configuração da imagem Docker
└── docker-compose.yml              # Orquestração da App + Banco Postgres
```

---

## 🚀 Como Executar o Projeto

### Opção 1: Via Docker (Recomendado)

Esta opção sobe a aplicação e o banco de dados PostgreSQL automaticamente.

1. Certifique-se de ter o **Docker** instalado.
2. Na raiz do projeto, execute:

```bash
docker-compose up --build
```

3. Acesse em: `http://localhost:9899`

---

### Opção 2: Localmente (Perfil de Desenvolvimento)

Usa o banco **H2** (em arquivo local) para facilitar os testes rápidos.

1. Execute o comando Maven:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=DEV
```

2. O console do banco H2 estará disponível em:
   - **URL:** `http://localhost:9899/h2-console`
   - **JDBC URL:** `jdbc:h2:file:./data/ecoponto-dev`

---

## 🔐 Autenticação e Segurança

A API utiliza **JWT**. Exceto pelos endpoints abaixo, todas as rotas exigem o header `Authorization`.

### Rotas Públicas

| Método | Endpoint                    | Descrição                  |
|--------|-----------------------------|----------------------------|
| POST   | `/api/auth/registrar`       | Criação de conta           |
| POST   | `/api/auth/login`           | Geração de Token           |
| POST   | `/api/associacoes`          | Cadastro de nova associação |
| GET    | Swagger UI                  | Documentação interativa    |
| GET    | H2 Console                  | Console do banco (DEV)     |

---
## 🔄 Fluxo do Sistema

1. **Cadastro e Login**: Usuários se registram como Doadores ou Associações.
2. **Publicação de Itens**: O Doador cadastra materiais (ex: papelão, plástico) detalhando peso e local de retirada.
3. **Gerenciamento de Coletas**: Associações visualizam os itens disponíveis no sistema para planejar suas rotas de coleta.
4. **Sustentabilidade**: O sistema conecta as pontas, garantindo que o resíduo chegue ao destino correto de reciclagem.

---

## 📚 Exemplos de Uso

### 1. Registrar Usuário

O registro cria o perfil de acesso e os dados do doador simultaneamente.

```bash
curl -X POST http://localhost:9899/api/auth/registrar \
  -H "Content-Type: application/json" \
  -d '{
        "nome": "João Silva",
        "email": "joao@email.com",
        "senha": "senha123",
        "telefone": "(11) 99999-9999",
        "cpf": "123.456.789-00"
      }'
```

### 2. Login (Obter Token)

Use as credenciais registradas para receber o Bearer Token necessário para rotas protegidas.

```bash
curl -X POST http://localhost:9899/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
        "email": "joao@email.com",
        "senha": "senha_segura"
      }'
```

> **Nota:** Copie o valor de `token` retornado para usar nos próximos comandos.

### 3. Criar Item (Requer Token)

```bash
curl -X POST http://localhost:9899/api/itens \
  -H "Authorization: Bearer TOKEN_RECEBIDO" \
  -H "Content-Type: application/json" \
  -d '{
        "material": "Plástico PET",
        "pesoEmKg": 2.5,
        "qtdVolume": 10,
        "urlFoto": "http://link-da-imagem.com/foto.jpg",
        "enderecoRetirada": "Rua das Flores, 123",
        "doadorId": 1
      }'
```

### 3. Registrar uma Associação (Público)

```bash
curl -X POST http://localhost:9899/api/associacoes \
  -H "Content-Type: application/json" \
  -d '{
        "nome": "Associação Recicla Mais",
        "cnpj": "12.345.678/0001-90",
        "email": "contato@reciclamais.org",
        "telefone": "(11) 4444-4444",
        "endereco": "Av. Industrial, 500"
      }'
```

---

## 📖 Documentação e Testes

A documentação interativa das rotas (Swagger) pode ser acessada em:

👉 [http://localhost:9899/swagger-ui.html](http://localhost:9899/swagger-ui.html)

**🚀 Importe o arquivo postman_collection.json no seu Postman para testar todos os fluxos prontamente!**



