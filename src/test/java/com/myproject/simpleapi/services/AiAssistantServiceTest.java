package com.myproject.simpleapi.services;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openaiofficial.OpenAiOfficialChatModel;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class AiAssistantServiceTest {

    @Autowired
    private AiAssistantService aiAssistantService;

    @MockitoBean
    private OpenAiOfficialChatModel chatModel;

    private final String MOCK_USER_QUESTION = "My question";

    private final String MOCK_AI_RESPONSE = "This is a test response";

    @Test
    public void shouldReturnResponseFromTheModel() {
        when(chatModel.chat(anyString())).thenReturn(MOCK_AI_RESPONSE);

        String result = aiAssistantService.ask(MOCK_USER_QUESTION);

        assertNotNull(result);
        assertTrue(result.equals(MOCK_AI_RESPONSE));
    }

    @Test
    public void shouldHandleConversationWithContext() {
        String conversationId = aiAssistantService.startConversation();
        ChatResponse mockChatResponse = Mockito.mock(ChatResponse.class);
        when(chatModel.chat(anyList())).thenReturn(mockChatResponse);
        when(chatModel.chat(anyList()).aiMessage()).thenReturn(new AiMessage(MOCK_AI_RESPONSE))
                .thenReturn(new AiMessage(MOCK_AI_RESPONSE + " 2"));

        String result1 = aiAssistantService.chat(conversationId, MOCK_USER_QUESTION);
        String result2 = aiAssistantService.chat(conversationId, MOCK_USER_QUESTION + " 2");

        List<ChatMessage> fullConversation = aiAssistantService.getHistory(conversationId);
        assertFalse(fullConversation.isEmpty());
        assertTrue(result1.equals(MOCK_AI_RESPONSE));
        assertTrue(result2.equals(MOCK_AI_RESPONSE + " 2"));
        assertTrue(fullConversation.get(0).getClass() == UserMessage.class);
        assertTrue(fullConversation.get(1).getClass() == AiMessage.class);
        assertTrue(fullConversation.get(2).getClass() == UserMessage.class);
        assertTrue(fullConversation.get(3).getClass() == AiMessage.class);
    }

    @Test
    public void shouldGenerataConversationId() {
        String resultFirstConversation = aiAssistantService.startConversation();
        String resultSecondConversation = aiAssistantService.startConversation();

        assertNotNull(resultFirstConversation);
        assertNotNull(resultSecondConversation);
        assertFalse(resultFirstConversation.equals(resultSecondConversation));
    }

    @Test
    public void shouldReturnTrueForExistingConversation() {
        String conversationId = aiAssistantService.startConversation();

        Boolean result = aiAssistantService.conversationExists(conversationId);

        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseForNonExistingConversation() {
        Boolean result = aiAssistantService.conversationExists("nonExistingId");

        assertFalse(result);
    }

    @Test
    public void shouldClearConversation() {
        String conversationId = aiAssistantService.startConversation();

        aiAssistantService.clearConversation(conversationId);

        assertFalse(aiAssistantService.conversationExists(conversationId));
    }
}
