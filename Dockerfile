# =============================================================
# Users Management (Spring Boot + Hexagonal) — imagen para Render
# Etapa 1: compila con Maven y JDK 17. Etapa 2: solo el JRE 17.
# =============================================================
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Descarga dependencias primero para aprovechar la caché de Docker
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B -q package -DskipTests -Djacoco.skip=true

FROM eclipse-temurin:17-jre
WORKDIR /app

RUN useradd --system --create-home appuser
COPY --from=build /app/target/users-management-*.jar app.jar
USER appuser

# Render inyecta PORT; application.properties lo usa en server.port
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "/app/app.jar"]
