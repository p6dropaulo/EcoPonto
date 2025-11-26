# EcoPonto

Sistema de gestão de coleta seletiva que conecta doadores de materiais recicláveis a associações de reciclagem.

---

## 📌 Objetivo do Projeto

O EcoPonto facilita a doação de materiais recicláveis, permitindo que:

- ♻️ **Doadores** registrem itens disponíveis para coleta  
- 🏭 **Associações** visualizem e coletem materiais  
- 🌱 **Comunidade** contribua para a sustentabilidade ambiental  

### 🌍 Valor Social

- Redução do desperdício por meio da reutilização  
- Apoio a associações de reciclagem locais  
- Maior acesso a materiais recicláveis  
- Promoção da consciência ambiental  

---

## 🧩 Pré-requisitos

- **Java 17+**
- **Maven 3.6+** (ou usar `mvnw`)
- **PostgreSQL** (ou Docker)

---

## ⚙️ Configuração do Ambiente

### 1. Copiar arquivo de configuração

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

### 2. Configurar variáveis de ambiente

#### Linux/macOS

```bash
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=ecoponto
export DB_USER=seu_usuario
export DB_PASS=sua_senha
```

#### Windows (CMD)

```cmd
set DB_HOST=localhost
set DB_PORT=5432
set DB_NAME=ecoponto
set DB_USER=seu_usuario
set DB_PASS=sua_senha
```

#### Windows (PowerShell)

```powershell
$env:DB_HOST="localhost"
$env:DB_PORT="5432"
$env:DB_NAME="ecoponto"
$env:DB_USER="seu_usuario"
$env:DB_PASS="sua_senha"
```

---

## 🚀 Executar a Aplicação

### Linux/macOS

```bash
./mvnw spring-boot:run
```

### Windows

```cmd
mvnw.cmd spring-boot:run
```

A aplicação estará disponível em: 👉 http://localhost:9899

---

## 📚 Documentação da API

Acesse via Swagger: 👉 http://localhost:9899/swagger-ui.html

---

## 🧪 Exemplos de Uso (cURL)

### Registrar Doador

```bash
curl -X POST http://localhost:9899/api/doadores \
  -H "Content-Type: application/json" \
  -d '{
        "nome": "João Silva",
        "email": "joao@email.com",
        "telefone": "(11) 99999-9999",
        "cpf": "123.456.789-00"
      }'
```

### Registrar Item

```bash
curl -X POST http://localhost:9899/api/itens/doador/1 \
  -H "Content-Type: application/json" \
  -d '{
        "material": "Plástico",
        "pesoEmKg": 2.5,
        "qtdVolume": 10,
        "enderecoRetirada": "Rua das Flores, 123"
      }'
```

### Listar Itens Disponíveis

```bash
curl -X GET http://localhost:9899/api/associacoes/itens-disponiveis
```

### Registrar Associação

```bash
curl -X POST http://localhost:9899/api/associacoes \
  -H "Content-Type: application/json" \
  -d '{
        "name": "Associação Verde",
        "cnpj": "12.345.678/0001-90",
        "email": "contato@verde.org",
        "phone": "(11) 8888-8888",
        "address": "Av. Sustentável, 456"
      }'
```

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/backend/ecoponto/
│   │   ├── controller/   # Endpoints REST
│   │   ├── service/      # Regras de negócio
│   │   ├── model/        # Entidades JPA
│   │   ├── repository/   # Acesso a dados
│   │   └── dto/          # Data Transfer Objects
│   └── resources/
│       ├── application.properties.example
│       └── data.sql      # Dados de exemplo
└── test/                 # Testes unitários


