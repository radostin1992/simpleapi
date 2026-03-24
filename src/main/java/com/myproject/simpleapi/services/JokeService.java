package com.myproject.simpleapi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.myproject.simpleapi.entities.JokeFromExtApi;

@Service
public class JokeService {

    Logger logger = LoggerFactory.getLogger(JokeService.class);

    private final String EXT_JOKE_URL = "https://official-joke-api.appspot.com/";

    @Autowired
    private RestTemplate restTemplate;

    public String getRandomJokeFromExtApi(String joketype) {
        // RestTemplate restTemplate = new RestTemplate();
        String extJokeEndpoint = "jokes/"+ joketype +"/random";

        JokeFromExtApi[] response;
        try {
            response = restTemplate.getForObject(EXT_JOKE_URL + extJokeEndpoint, JokeFromExtApi[].class);
        } catch (Exception e) {
            logger.warn("Problem calling the external joke service", e);
            return "";
        }

        return "- " + response[0].setup() + "\n- " + response[0].punchline();
    }
}
