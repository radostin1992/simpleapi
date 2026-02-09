FROM eclipse-temurin:21
RUN addgroup spring_group && useradd -G spring_group spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]