package com.myproject.simpleapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleapiController {
    Logger logger = LoggerFactory.getLogger(SimpleapiController.class);
    
    @GetMapping("/")
    public String index() {
        logger.info("Received request for index endpoint");
        return "Greetings from simple api Spring Boot application!";
    }

    @GetMapping("/joke")
    public String joke() {
        String[] jokes = {
                "Why did the chicken cross the road? To get to the other side!",
                "Why did the scarecrow win an award? Because he was outstanding in his field!",
                "Why don't scientists trust atoms? Because they make up everything!"
        };

        int randomIndex = (int) (Math.random() * jokes.length);

        logger.info("Received request for joke endpoint");
        return jokes[randomIndex];
    }
}
