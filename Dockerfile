FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
MAINTAINER Tarun Dhraik
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package
FROM java:8
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/generate-exchange-rate-0.1.jar /app/
ENTRYPOINT ["java", "-jar", "generate-exchange-rate-0.1.jar"]