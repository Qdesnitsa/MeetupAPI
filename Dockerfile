#Dockerfile - набор инструкций для запуска виртуальной машины и приложения на ней
# openjdk - виртуальная машина (окружение)
FROM openjdk:11
COPY target/spring-boot-docker.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]