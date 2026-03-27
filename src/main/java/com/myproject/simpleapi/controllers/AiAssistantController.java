package com.myproject.simpleapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.myproject.openapi.api.AiApi;
import com.myproject.openapi.model.AiAskRequest;
import com.myproject.openapi.model.AiAskResponse;
import com.myproject.simpleapi.services.AiAssistantService;


@Controller
public class AiAssistantController implements AiApi {

    @Autowired
    private AiAssistantService aiAssistantService;

    Logger logger = LoggerFactory.getLogger(AiAssistantController.class);

    @Override
    public ResponseEntity<AiAskResponse> aiAsk(AiAskRequest aiAskRequest) {
        logger.info("AI ask request received");

        String prompt = aiAskRequest.getPrompt();
        if (prompt == null || prompt.isBlank()) {
            logger.warn("Invalid prompt message");
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String aiResponse = aiAssistantService.ask(prompt);

        if (aiResponse == null) {
            logger.warn("Problem with getting result from the AI model");
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(null);
        }

        AiAskResponse aiAskResponse = new AiAskResponse();
        aiAskResponse.setReponse(aiResponse);

        return ResponseEntity.status(HttpStatus.OK).body(aiAskResponse);
    }

}
