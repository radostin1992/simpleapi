package com.myproject.simpleapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.myproject.openapi.api.DefaultApi;

@Controller
public class SimpleapiController implements DefaultApi{
    Logger logger = LoggerFactory.getLogger(SimpleapiController.class);
    
    @Override
    public ResponseEntity<String> index() {
        logger.info("Received request for index endpoint");
        return ResponseEntity.ok("Greetings from simple api Spring Boot application!");
    }
}
