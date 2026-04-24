package com.myproject.simpleapi.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import dev.langchain4j.model.openaiofficial.OpenAiOfficialChatModel;

@Configuration
@Profile("default")
public class LangChainConfig {

    @Value("${GITHUB_TOKEN}")
    private String githubToken;

    @Bean
    public OpenAiOfficialChatModel openAiOfficialChatModel() {
        return OpenAiOfficialChatModel.builder()
                .baseUrl("https://models.github.ai/inference")
                .apiKey(githubToken)
                .modelName("gpt-4.1-nano")
                .build();
    }    
}
