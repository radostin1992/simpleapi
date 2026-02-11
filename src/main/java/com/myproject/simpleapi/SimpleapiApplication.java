package com.myproject.simpleapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SimpleapiApplication {

	/*
	 * Basic info:
	 * - run the application (local installation for development) - ./mvnw
	 * spring-boot:run
	 * - run tests - .\mvnw.cmd test
	 * - rebuild app - ./mvnw clean install
	 * - curl from terminal - curl http://localhost:8080
	 * 
	 * Development with docker:
	 * - to start the application in a docker container with hot reload - 
	 * 		docker compose -f docker-compose.dev.yml up
	 * - to stop the application in a docker container 
	 * 		docker compose -f docker-compose.dev.yml down
	 * 
	 * To test "production" (docker container) that is used in render.com:
	 * - docker build -t myproject/simpleapi-prod .
	 * - docker run -p 8080:8080 myproject/simpleapi-prod
	 * 
	 * todo and ideas:
	 * - secure the spring endpoints
	 * - upgrade java version ?
	 * - add database and migrator application (flyway)
	 * - add more endpoints (e.g. POST, PUT, DELETE)
	 * - add more complex logic to the endpoints (e.g. call external API, process
	 * data, etc.)
	 */
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(SimpleapiController.class);

		ApplicationContext ctx = SpringApplication.run(SimpleapiApplication.class, args);

		logger.info("Found {} beans provided by Spring Boot", ctx.getBeanDefinitionCount());

		// For debugging purposes, print all the beans provided by Spring Boot
		// String[] beanNames = ctx.getBeanDefinitionNames();
		// Arrays.sort(beanNames);
		// for (String beanName : beanNames) {
		// System.out.println(beanName);
		// }
	}

}
