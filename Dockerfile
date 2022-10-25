FROM openjdk:11
COPY target/spring-boot-docker.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]