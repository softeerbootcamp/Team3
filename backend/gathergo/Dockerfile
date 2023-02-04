FROM openjdk:11
ARG JAR_FILE=build/libs/gathergo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENV SPRING_PROFILES_ACTIVE=dev
ENTRYPOINT ["java","-jar","/app.jar"]
