
# SimpleAPI
What it will do - nobody knows ... ¯¯\\_(ツ)_/¯¯ 

Core objectives:
- Apply and reinforce practical knowledge and best practices
- Use only free-tier or open-source services (no paid dependencies)
- Serve as the backend component of a full-stack project
- Minimize AI-generated code to ensure hands-on learning and implementation

### What's already done:
- [x] Created simple java spring project (https://start.spring.io/)
- [x] Added some basic functionality with tests (https://spring.io/guides/gs/spring-boot#initial)
- [x] Served health enpoint from the actuator (basic monitoring)
- [x] Added cron job (initial idea to keep the free server running https://spring.io/guides/gs/scheduling-tasks)
- [x] Containerized the application (https://spring.io/guides/gs/spring-boot-docker)
- [x] Deployed the api (the choosen service is https://render.com/)
- [x] Added basic logging and formating in files
- [x] Added hot realoding/swapping of the working files (https://docs.spring.io/spring-boot/reference/using/devtools.html#using.devtools.restart.customizing-the-classload and https://docs.spring.io/spring-boot/how-to/hotswapping.html#page-title)
- [x] Prepared the application for more services by adding composer.yml
- [x] Upgraded the java (from 21 to 25)
- [x] Added a README file
- [x] Added database with credentials taken from .env file (not published) https://www.baeldung.com/spring-boot-postgresql-docker
- [x] Added migrator application (Flyway) ( https://medium.com/@AlexanderObregon/using-spring-boot-with-flyway-to-manage-database-migrations-8180ce0c9230 and https://davidbytyqi.medium.com/simplifying-database-migrations-with-flyway-and-docker-42ef5a971178)
- [x] Added OpenAPI Generator to generate api server side (https://www.baeldung.com/java-openapi-generator-server)
- [x] Added more endpoints to cover basic CRUD operations
- [x] Open api expose the swagger ui (https://www.baeldung.com/spring-rest-openapi-documentation)
- [x] Secure the Spring endpoints (https://dev.to/jps27cse/spring-security-for-beginners-the-easiest-guide-youll-ever-read-2cib)
- [x] Making external API calls (jokes are now taken from https://www.freepublicapis.com/official-joke-api)
- [x] Implement AI into the api using LangChain4j calling GitHub free models (https://github.com/microsoft/LangChain4j-for-Beginners)
- [x] Added AI conversation endpoint with chat memory

### Basic Info
#### run the application (local installation for development without other services)
```bash
./mvnw spring-boot:run
```

#### Run Tests
```bash
.\mvnw.cmd test
```

#### Rebuild App
```bash
./mvnw clean install
```

#### Test with cURL from terminal
```bash
curl http://localhost:8080 -H 'Authorization: Basic c3dhZ2dlcjpzd2FnZ2VyMTIz'
curl http://localhost:8080/user?id=1 -H 'Authorization: Basic c3dhZ2dlcjpzd2FnZ2VyMTIz'
```
NOTE: Using 'swagger' authentication

### Test from swagger UI page
Go to https://simpleapi-11av.onrender.com/swagger-ui.html or http://localhost:8080/swagger-ui.html on local env
and for username use: 'swagger' for password use 'swagger123'
NOTE: deleting is forbidden 

### Development with Docker

#### to start the application in a docker container with hot reload
```bash
docker compose -f docker-compose.dev.yml up
```

#### to stop the application in a docker container
```bash
docker compose -f docker-compose.dev.yml down
```

### Production Testing

Build and run the production container (as used in render.com):

```bash
docker build -t myproject/simpleapi-prod .
docker run -p 8080:8080 --env-file .env-prod myproject/simpleapi-prod
```

### TODO & Ideas
- [ ] Add more complex logic (external API calls, data processing, etc.)
- [ ] Tests coverage report via github workflow ? 