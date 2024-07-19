FROM openjdk:17

ENV ACTIVE_PROFILE=dev

EXPOSE 8080

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-server", "-jar", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "app.jar", "-Dserver.port=8080"]