package com.example.sweezcustoms.config;

import com.example.sweezcustoms.entity.AiChat;
import com.example.sweezcustoms.entity.AiChatMessage;
import com.example.sweezcustoms.enums.AiRole;
import com.example.sweezcustoms.service.AiChatService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.List;
import java.util.UUID;

@Builder
@RequiredArgsConstructor
public class CustomPostgresChatMemory implements ChatMemory {
    private final AiChatService aiChatService;
    private final Long maxMessages;

    @Override
    public void add(String conversationId, List<Message> messages) {
        UUID chatId = UUID.fromString(conversationId);
        aiChatService.findOrCreateAiChat(chatId);

        AiChat chatRef = AiChat.builder().id(chatId).build();
        messages.forEach(message -> {
            AiChatMessage aiChatMessage = AiChatMessage.builder()
                    .aiChat(chatRef)
                    .text(message.getText())
                    .aiRole(AiRole.valueOf(message.getMessageType().name()))
                    .build();
            aiChatService.saveChatMessage(aiChatMessage);
        });
    }

    @Override
    public List<Message> get(String conversationId) {
        return aiChatService.getMessages(UUID.fromString(conversationId), maxMessages)
                .stream()
                .map(this::validate)
                .toList();
    }

    private Message validate(AiChatMessage aiChatMessage){
        switch (aiChatMessage.getAiRole()){
            case USER -> {
                return new UserMessage(aiChatMessage.getText());
            }
            case ASSISTANT ->{
                return new AssistantMessage(aiChatMessage.getText());
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public void clear(String conversationId) {

    }
}
