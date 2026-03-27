package com.myproject.simpleapi.services;

import org.springframework.stereotype.Service;

import dev.langchain4j.model.openaiofficial.OpenAiOfficialChatModel;

@Service
public class AiAssistantService {

    private final OpenAiOfficialChatModel chatModel;

    public AiAssistantService(OpenAiOfficialChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String ask(String prompt) {
        String answer = chatModel.chat(prompt);

        return answer;
    }
}
