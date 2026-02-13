#
# Build stage
#
FROM eclipse-temurin:25.0.2_10-jdk-noble AS build
ENV HOME=/usr/app
ENV POSTGRES_URL=${POSTGRES_URL}
ENV POSTGRES_USER=${POSTGRES_USER}
ENV POSTGRES_PASSWORD=${POSTGRES_PASSWORD}
RUN mkdir -p $HOME
WORKDIR $HOME
COPY . $HOME
RUN --mount=type=cache,target=/root/.m2 chmod +x ./mvnw && ./mvnw -f $HOME/pom.xml clean package -e

#
# Package stage
#
FROM eclipse-temurin:25.0.2_10-jre-noble 
ARG JAR_FILE=/usr/app/target/*.jar
COPY --from=build $JAR_FILE /app/runner.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/runner.jar"]
