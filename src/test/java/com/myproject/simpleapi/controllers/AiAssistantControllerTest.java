package com.myproject.simpleapi.controllers;

import static org.hamcrest.Matchers.containsString;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.myproject.simpleapi.security.Authenticator;
import com.myproject.simpleapi.services.AiAssistantService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class AiAssistantControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Authenticator authenticator;

    @MockitoBean
    private AiAssistantService aiAssistantService;

    // ASK endpoint
    @Test
    public void testAsk() throws Exception {
        String reqContent = """
                {
                    "prompt": "User question"
                }
                """;

        when(aiAssistantService.ask("User question")).thenReturn("Ai response");

        mvc.perform(post("/ai/ask").contentType(MediaType.APPLICATION_JSON)
                .content(reqContent)
                .headers(authenticator.getAuthenticatedHeader()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ai response")));
    }

    @Test
    public void testAskWithInvalidPrompt() throws Exception {
        String reqContent = """
                {
                    "prompt": ""
                }
                """;

        mvc.perform(post("/ai/ask").contentType(MediaType.APPLICATION_JSON)
                .content(reqContent)
                .headers(authenticator.getAuthenticatedHeader()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAskButAiServiceIsUnavailble() throws Exception {
        String reqContent = """
                {
                    "prompt": "User question"
                }
                """;

        when(aiAssistantService.ask("")).thenReturn(null);

        mvc.perform(post("/ai/ask").contentType(MediaType.APPLICATION_JSON)
                .content(reqContent)
                .headers(authenticator.getAuthenticatedHeader()))
                .andExpect(status().isFailedDependency());
    }

    // CONVERSATION endpoint
    @Test
    public void testConversationWithoutConversationId() throws Exception {
        String reqContent = """
                {
                    "prompt": "User question",
                    "conversationId": ""
                }
                """;

        when(aiAssistantService.startConversation()).thenReturn("conversationId");
        when(aiAssistantService.chat("conversationId", "User question")).thenReturn("Ai response");

        mvc.perform(post("/ai/conversation").contentType(MediaType.APPLICATION_JSON)
                .content(reqContent)
                .headers(authenticator.getAuthenticatedHeader()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ai response")))
                .andExpect(content().string(containsString("conversationId")));
    }

    @Test
    public void testConversationWithConversationId() throws Exception {
        String reqContent = """
                {
                    "prompt": "User question",
                    "conversationId": "myConversationId"
                }
                """;

        // when(aiAssistantService.startConversation()).thenReturn("myConversationId");
        // - won't be called
        when(aiAssistantService.chat("myConversationId", "User question")).thenReturn("Ai response");

        mvc.perform(post("/ai/conversation").contentType(MediaType.APPLICATION_JSON)
                .content(reqContent)
                .headers(authenticator.getAuthenticatedHeader()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ai response")))
                .andExpect(content().string(containsString("myConversationId")));

        Mockito.verify(aiAssistantService, Mockito.times(0)).startConversation();

    }

    @Test
    public void testConversationWithInvalidRequest() throws Exception {
        String reqContent = """
                {
                    "prompt": "",
                    "conversationId": ""
                }
                """;

        mvc.perform(post("/ai/conversation").contentType(MediaType.APPLICATION_JSON)
                .content(reqContent)
                .headers(authenticator.getAuthenticatedHeader()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testConversationWhenAiServiceUnavalable() throws Exception {
        String reqContent = """
                {
                    "prompt": "User question",
                    "conversationId": ""
                }
                """;

        when(aiAssistantService.startConversation()).thenReturn("conversationId");
        when(aiAssistantService.chat("conversationId", "User question")).thenReturn(null);

        mvc.perform(post("/ai/conversation").contentType(MediaType.APPLICATION_JSON)
                .content(reqContent)
                .headers(authenticator.getAuthenticatedHeader()))
                .andExpect(status().isFailedDependency());
    }
}
