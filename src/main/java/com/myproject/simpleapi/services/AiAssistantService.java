package com.myproject.simpleapi.services;

import org.springframework.stereotype.Service;

import dev.langchain4j.model.openaiofficial.OpenAiOfficialChatModel;

@Service
public class AiAssistantService {
    public String ask(String prompt) {

        String githubToken = System.getenv("GITHUB_TOKEN");
        if (githubToken == null || githubToken.isEmpty()) {
           return null;
        }

        OpenAiOfficialChatModel model = OpenAiOfficialChatModel.builder()
                .baseUrl("https://models.github.ai/inference")
                .apiKey(githubToken)
                .modelName("gpt-4.1-nano")
                .build();
        
        String answer = model.chat(prompt);

        return answer;
    }
}
