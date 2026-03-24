package com.myproject.simpleapi.controllers;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.myproject.openapi.api.JokeApi;
import com.myproject.simpleapi.services.JokeService;

@Controller
public class JokeController implements JokeApi {

    Logger logger = LoggerFactory.getLogger(JokeController.class);

    @Autowired
    private JokeService jokeService;

    private static final Set<String> ALLOWED = Set.of("general", "knock-knock", "programming", "dad");

    @Override
    public ResponseEntity<String> joke(String joketype) {
        logger.info("Received request for joke endpoint");

        if (!ALLOWED.contains(joketype)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid joketype. Possible options are: " + ALLOWED.toString());
        }

        String result = jokeService.getRandomJokeFromExtApi(joketype);

        if (result.isEmpty()){
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Problem getting a joke, sorry :(");
        }
        
        return ResponseEntity.ok(result);
    }
}
