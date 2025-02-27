FROM openjdk:21-jdk
ARG JAR_FILE=target/spring-voltage-monitor-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_voltage.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_voltage.jar"]