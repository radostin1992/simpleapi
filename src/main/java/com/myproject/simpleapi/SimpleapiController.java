package com.myproject.simpleapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleapiController {
    @GetMapping("/")
    public String index() {
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

        return jokes[randomIndex];
    }
}
