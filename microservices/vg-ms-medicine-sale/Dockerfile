FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 9097
ENTRYPOINT ["java", "-Xms64m", "-Xmx150m", "-XX:+UseSerialGC", "-jar", "app.jar"]
