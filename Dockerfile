#
# Build stage
#
FROM maven:3-eclipse-temurin-21 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM maven:3-eclipse-temurin-21
RUN addgroup spring_group && useradd -G spring_group spring
USER spring:spring
COPY --from=build /home/app/target/simpleapi-0.0.1-SNAPSHOT.jar /usr/local/lib/simpleapi.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/simpleapi.jar"]
