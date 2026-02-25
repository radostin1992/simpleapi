package com.myproject.simpleapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.myproject.openapi.api.JokeApi;

@Controller
public class JokeController implements JokeApi {
    // functionality needs to be moved to a service once external api is called to fetch jokes
    Logger logger = LoggerFactory.getLogger(JokeController.class);

    private final String[] jokes = {
            "Why did the chicken cross the road? To get to the other side!",
            "Why did the scarecrow win an award? Because he was outstanding in his field!",
            "Why don't scientists trust atoms? Because they make up everything!"
    };

    @Override
    public ResponseEntity<String> joke() {
        int randomIndex = (int) (Math.random() * jokes.length);

        logger.info("Received request for joke endpoint");
        return ResponseEntity.ok(jokes[randomIndex]);
    }
}
