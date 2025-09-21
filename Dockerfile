FROM openjdk:17-jdk-slim
WORKDIR /app

COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

COPY build/libs/PickBusBackend-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["/wait-for-it.sh", "mysql:3306", "--", "/wait-for-it.sh", "redis:6379", "--", "java", "-jar", "app.jar"]