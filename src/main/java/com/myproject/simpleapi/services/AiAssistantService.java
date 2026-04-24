package com.myproject.simpleapi.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openaiofficial.OpenAiOfficialChatModel;

@Service
public class AiAssistantService {

    private final OpenAiOfficialChatModel chatModel;

    private final Map<String, ChatMemory> conversationMemories;

    private static final int MAX_MESSAGES = 10;

    public AiAssistantService(OpenAiOfficialChatModel chatModel) {
        this.chatModel = chatModel;
        this.conversationMemories = new ConcurrentHashMap<>();

    }

    /** 
     * Simple ask AI endpoint no context saved.
     * Workflow - ask -> get Ai response - nothing is stored in memory
     * 
     *  @param prompt is the question you want to ask the AI model 
     *  @return AI answer
     * */
    public String ask(String prompt) {
        String answer = chatModel.chat(prompt);

        return answer;
    }

    /**
     * Send a message within an existing conversation.
     *
     * @param conversationId the conversation ID
     * @param message        the user message
     * @return AI response
     */
    public String chat(String conversationId, String message) {
        ChatMemory memory = conversationMemories.computeIfAbsent(
                conversationId,
                id -> MessageWindowChatMemory.withMaxMessages(MAX_MESSAGES));

        // Add user message to memory
        UserMessage userMessage = UserMessage.from(message);
        memory.add(userMessage);

        // Get all messages for context
        List<ChatMessage> messages = memory.messages();

        // Generate response using chat method with message list
        AiMessage aiMessage = chatModel.chat(messages).aiMessage();

        // Add AI response to memory
        memory.add(aiMessage);

        return aiMessage.text();
    }

    /**
     * Start a new conversation and return a unique conversation ID.
     *
     * @return new conversation ID
     */
    public String startConversation() {
        String conversationId = UUID.randomUUID().toString();
        ChatMemory memory = MessageWindowChatMemory.withMaxMessages(MAX_MESSAGES);
        conversationMemories.put(conversationId, memory);
        return conversationId;
    }

    /**
     * Get conversation history for a given conversation ID.
     *
     * @param conversationId the conversation ID
     * @return list of chat messages
     */
    public List<ChatMessage> getHistory(String conversationId) {
        ChatMemory memory = conversationMemories.get(conversationId);
        return memory != null ? memory.messages() : List.of();
    }

    /**
     * Clear conversation history for a given conversation ID.
     *
     * @param conversationId the conversation ID
     */
    public void clearConversation(String conversationId) {
        conversationMemories.remove(conversationId);
    }

    /**
     * Check if a conversation exists.
     *
     * @param conversationId the conversation ID
     * @return true if conversation exists
     */
    public boolean conversationExists(String conversationId) {
        return conversationMemories.containsKey(conversationId);
    }
}
