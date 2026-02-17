package com.myproject.simpleapi.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.myproject.simpleapi.actuator.ServerHealth;

@Component
public class CheckServerStatus {

	private static final Logger log = LoggerFactory.getLogger(CheckServerStatus.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    // every 10 minutes
	@Scheduled(fixedRate = 600000)
	public void reportCurrentTime() {
        // make request to actuator health endpoint and log the response
        RestTemplate restTemplate = new RestTemplate();
        ServerHealth response = restTemplate.getForObject("http://localhost:8080/actuator/health", ServerHealth.class);

        log.info("The time is now {} and the server is {}", dateFormat.format(new Date()), response.getStatus());
	}
}
