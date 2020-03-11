FROM adoptopenjdk/openjdk13:x86_64-alpine-jre-13.0.1_9

VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]