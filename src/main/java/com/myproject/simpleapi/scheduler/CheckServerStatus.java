package com.myproject.simpleapi.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.myproject.simpleapi.actuator.ServerHealth;
import com.myproject.simpleapi.security.Authenticator;

@Component
public class CheckServerStatus {

    private static final Logger log = LoggerFactory.getLogger(CheckServerStatus.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private Authenticator authenticator;

    // every 10 minutes
    @Scheduled(fixedRate = 600000)
    public void reportCurrentTime() {
        // make request to actuator health endpoint and log the response
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(authenticator.getAuthenticatedHeader());

        ServerHealth response = restTemplate.exchange("http://localhost:8080/actuator/health", HttpMethod.GET, entity, ServerHealth.class).getBody();

        log.info("The time is now {} and the server is {}", dateFormat.format(new Date()), response.getStatus());
    }
}
