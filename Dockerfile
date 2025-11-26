# Dockerfile para aplicação EcoPonto
FROM openjdk:17-jdk-slim

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos do Maven
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Dar permissão de execução ao mvnw
RUN chmod +x ./mvnw

# Baixar dependências
RUN ./mvnw dependency:go-offline

# Copiar código fonte
COPY src ./src

# Construir aplicação
RUN ./mvnw clean package -DskipTests

# Expor porta da aplicação
EXPOSE 9899

# Comando para executar a aplicação
CMD ["java", "-jar", "target/ecoponto-0.0.1-SNAPSHOT.jar"]