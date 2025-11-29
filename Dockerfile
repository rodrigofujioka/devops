# Etapa de build
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia os arquivos do projeto
COPY pom.xml .
COPY src ./src

# Constrói o aplicativo
RUN mvn clean package -DskipTests

# Etapa de execução
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copia o JAR construído da etapa de build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta que o aplicativo irá rodar
EXPOSE 8080

# Comando para executar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]
