# Etapa 1 - Build da aplicação com Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copia os arquivos de definição
COPY pom.xml .
COPY src ./src

# Executa o build do projeto
RUN mvn clean package -DskipTests

# Etapa 2 - Executa a aplicação
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copia o JAR gerado do build anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta do Spring Boot
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]