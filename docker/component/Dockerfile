FROM adoptopenjdk/openjdk13:jdk-13.0.2_8-alpine

VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENV SPRING_PROFILES_ACTIVE prod

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]