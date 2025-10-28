# Etapa 1: Build da aplicação
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /build

# Copia apenas a pasta app do repositório (onde está o pom.xml)
COPY app /build

# Executa o build do projeto dentro da pasta app
WORKDIR /build
RUN mvn -f pom.xml clean package -DskipTests

# Etapa 2: Executa a aplicação
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copia o JAR gerado da etapa anterior
COPY --from=build /build/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]