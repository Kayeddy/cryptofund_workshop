FROM eclipse-temurin:17-jdk-alpine

ARG WAR_FILE=./target/*.jar

COPY ${WAR_FILE} cryptofund-user.jar

CMD ["java", "-jar", "cryptofund-user.jar"]
