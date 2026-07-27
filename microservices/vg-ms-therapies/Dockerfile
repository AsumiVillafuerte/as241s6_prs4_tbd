FROM maven:3.9-eclipse-temurin-17-alpine AS builder
WORKDIR /build

COPY pom.xml .
RUN mvn -B -ntp dependency:go-offline

COPY src ./src
RUN mvn -B -ntp -DskipTests package && \
    rm -f target/*-original.jar

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=builder /build/target/*.jar /app/app.jar

EXPOSE 9094

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:+TieredCompilation", "-XX:TieredStopAtLevel=1", "-Xms128m", "-Xmx256m", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=200", "-jar", "/app/app.jar"]
