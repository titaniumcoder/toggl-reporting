FROM gradle as builder
WORKDIR /app
COPY . /app
RUN gradle stage

FROM openjdk:8-alpine as runtime
LABEL maintainer=rico
WORKDIR /app
COPY --from=builder /app/server/build/libs/*.jar /app/reporting.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app/reporting.jar"]
EXPOSE 8080
