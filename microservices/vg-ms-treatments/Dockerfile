# =====================================================
# Dockerfile Multi-Stage Ultra Optimizado
# =====================================================
# Objetivo: Reducir el tamaño a menos de 200MB
# =====================================================

# ===================================================
# ETAPA 1: BUILD (Compilación)
# ===================================================
FROM maven:3.9-eclipse-temurin-17-alpine AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src ./src
RUN mvn clean package -DskipTests -q && \
    rm -rf /root/.m2/repository

# ===================================================
# ETAPA 2: RUNTIME (Imagen final - Optimizada)
# ===================================================
FROM eclipse-temurin:17-jre-alpine

# Instalaciones mínimas
RUN apk add --no-cache dumb-init && \
    rm -rf /var/cache/apk/*

WORKDIR /app

# Copiar JAR
COPY --from=build /app/target/treatments-0.0.1-SNAPSHOT.jar app.jar

# Puerto
EXPOSE 9092

# Optimizaciones JVM agresivas para reducir memoria
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=40.0 -XX:+UseG1GC -XX:+UseStringDeduplication -XX:+ParallelRefProcEnabled -XX:G1HeapRegionSize=16M -XX:InitiatingHeapOccupancyPercent=35 -XX:+DisableExplicitGC"

# Ejecutar
ENTRYPOINT ["/usr/bin/dumb-init", "--"]
CMD ["java", "-jar", "app.jar"]