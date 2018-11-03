FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn clean package

FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/weather-spring-api.jar /app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]