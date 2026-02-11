
# SimpleAPI
What it will do - nobody knows ... ¯¯\\_(ツ)_/¯¯ 

The main idea is to exercise what I've seen and what I've learned 

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

- [ ] Secure the Spring endpoints
- [ ] Add database and migrator application (Flyway)
- [ ] Add more endpoints (POST, PUT, DELETE)
- [ ] Add more complex logic (external API calls, data processing, etc.)

### What's done:
- [x] Created simple java spring project
- [x] Added some basic functionality with tests
- [x] Served health enpoint from the actuator (basic monitoring)
- [x] Added cron job (initial idea to keep the free server running)
- [x] Added basic logging and formating in files
- [x] Containerized the application
- [x] Added hot realoding/swapping of the working files
- [x] Prepared the application for more services by adding composer.yml
- [x] Upgraded the java (from 21 to 25)  