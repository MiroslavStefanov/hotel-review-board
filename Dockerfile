FROM maven:3-jdk-11 AS build

COPY platform/pom.xml /build/
COPY platform/src /build/src/

WORKDIR /build

RUN --mount=type=cache,target=/root/.m2 mvn clean install -Dmaven.test.skip

FROM openjdk:11-jre-slim

COPY --from=build /build/target/hotel-review-board-0.0.1-SNAPSHOT.war /app/app.war

WORKDIR /app

ENTRYPOINT [ "java", "-jar", "app.war" ]
