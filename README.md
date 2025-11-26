EcoPonto Sistema de gestão de coleta seletiva que conecta doadores de materiais recicláveis com associações de reciclagem.

Objetivo do Projeto O EcoPonto facilita a doação de materiais recicláveis, permitindo que:

Doadores registrem itens disponíveis para coleta Associações visualizem e coletem materiais disponíveis Comunidade contribua para a sustentabilidade ambiental Valor Social Redução do desperdício através da reutilização Apoio a associações de reciclagem locais Facilita o acesso a materiais recicláveis Promove consciência ambiental na comunidade Pré-requisitos Java 17 ou superior Maven 3.6+ (ou usar o wrapper incluído) PostgreSQL (ou usar Docker) Configuração

Variáveis de Ambiente Copie o arquivo de exemplo:
cp src/main/resources/application.properties.example src/main/resources/application.properties Configure as variáveis de ambiente:

Linux/macOS:

export DB_HOST=localhost export DB_PORT=5432 export DB_NAME=ecoponto export DB_USER=seu_usuario export DB_PASS=sua_senha Windows (CMD):

set DB_HOST=localhost set DB_PORT=5432 set DB_NAME=ecoponto set DB_USER=seu_usuario set DB_PASS=sua_senha Windows (PowerShell):

$env:DB_HOST=“localhost” $env:DB_PORT=“5432” $env:DB_NAME=“ecoponto” $env:DB_USER=“seu_usuario” $env:DB_PASS=“sua_senha” 2. Executar a Aplicação Linux/macOS:

./mvnw spring-boot:run Windows:

mvnw.cmd spring-boot:run A aplicação estará disponível em: http://localhost:9899

Documentação da API Acesse a documentação Swagger em: http://localhost:9899/swagger-ui.html

Exemplos de Uso Registrar Doador curl -X POST http://localhost:9899/api/doadores
-H “Content-Type: application/json”
-d ‘{ “nome”: “João Silva”, “email”: “joao@email.com”, “telefone”: “(11) 99999-9999”, “cpf”: “123.456.789-00” }’ Registrar Item curl -X POST http://localhost:9899/api/itens/doador/1
-H “Content-Type: application/json”
-d ‘{ “material”: “Plástico”, “pesoEmKg”: 2.5, “qtdVolume”: 10, “enderecoRetirada”: “Rua das Flores, 123” }’ Listar Itens Disponíveis curl -X GET http://localhost:9899/api/associacoes/itens-disponiveis Registrar Associação curl -X POST http://localhost:9899/api/associacoes
-H “Content-Type: application/json”
-d ‘{ “name”: “Associação Verde”, “cnpj”: “12.345.678/0001-90”, “email”: “contato@verde.org”, “phone”: “(11) 8888-8888”, “address”: “Av. Sustentável, 456” }’ Testes Executar testes:

./mvnw test Docker (Opcional) Se preferir usar Docker:

docker-compose up -d Estrutura do Projeto src/ ├── main/ │ ├── java/com/backend/ecoponto/ │ │ ├── controller/ # Endpoints REST │ │ ├── service/ # Lógica de negócio │ │ ├── model/ # Entidades JPA │ │ ├── repository/ # Acesso a dados │ │ └── dto/ # Data Transfer Objects │ └── resources/ │ ├── application.properties.example │ └── data.sql # Dados de exemplo └── test/ # Testes unitários Contribuição Fork o projeto Crie uma branch para sua feature Commit suas mudanças Push para a branch Abra um Pull Request