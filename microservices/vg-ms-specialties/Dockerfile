FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /build

COPY pom.xml .
RUN mvn -B -ntp dependency:go-offline

COPY src ./src
RUN mvn -B -ntp -DskipTests package


FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder /build/target/app.jar /app/app.jar

EXPOSE 8087

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:+TieredCompilation", "-XX:TieredStopAtLevel=1", "-Xms128m", "-Xmx256m", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=200", "-jar", "/app/app.jar"]
