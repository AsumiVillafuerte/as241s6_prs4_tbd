# ─── Etapa 1: Compilación ────────────────────────────────────────────────────
# Usa Maven con JDK 17 para descargar dependencias y empaquetar el JAR
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Descarga dependencias primero (aprovecha caché de Docker si pom.xml no cambia)
COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src ./src
RUN mvn package -DskipTests -q

# ─── Etapa 2: Imagen de producción ───────────────────────────────────────────
# Solo JRE 17 para reducir el tamaño final (~200 MB vs ~600 MB con JDK)
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/vg-ms-shopping-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9096
ENTRYPOINT ["java", \
  "-Xms32m", "-Xmx120m", \
  "-XX:MaxMetaspaceSize=70m", \
  "-XX:ReservedCodeCacheSize=28m", \
  "-XX:MaxDirectMemorySize=20m", \
  "-XX:+UseSerialGC", \
  "-jar", "app.jar"]
