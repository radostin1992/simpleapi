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
