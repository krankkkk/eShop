FROM eclipse-temurin:17-jre-alpine

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 80
EXPOSE 443

ENTRYPOINT ["java","-jar","/app.jar"]
