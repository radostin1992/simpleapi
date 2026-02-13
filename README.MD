
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
- [x] Added database with credentials taken from .env file (not published)

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
curl http://localhost:8080
curl http://localhost:8080/user?id=1
```

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
docker run -p 8080:8080 myproject/simpleapi-prod
```

### TODO & Ideas
- [ ] Add migrator application (Flyway)
- [ ] Add Open api 
- [ ] Secure the Spring endpoints
- [ ] Add more endpoints (POST, PUT, DELETE)
- [ ] Add more complex logic (external API calls, data processing, etc.) 