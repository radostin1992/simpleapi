package com.myproject.simpleapi;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SimpleapiApplication {

	/*
	 * Basic info:
	 * - run the application - ./mvnw spring-boot:run
	 * - run tests - .\mvnw.cmd test
	 * - rebuild app - ./mvnw clean install
	 * - curl from terminal - curl http://localhost:8080
	 * 
	 * todo and ideas:
	 * - initialize git repo and push to github
	 * - secure the spring endpoints 
	 */
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SimpleapiApplication.class, args);

		System.out.println("Found " + ctx.getBeanDefinitionCount() + " beans provided by Spring Boot:");

		// For debugging purposes, print all the beans provided by Spring Boot
		// String[] beanNames = ctx.getBeanDefinitionNames();
		// Arrays.sort(beanNames);
		// for (String beanName : beanNames) {
		// 	System.out.println(beanName);
		// }
	}

}
