package com.example.sweezcustoms.utils.chainOfResponsibility.impl;

import com.example.sweezcustoms.entity.AiChatMessage;
import com.example.sweezcustoms.enums.AiRole;
import com.example.sweezcustoms.utils.chainOfResponsibility.AiChatMessageValidatorRole;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Component;

@Component
public class AiChatMessageValidatorAssistantRole implements AiChatMessageValidatorRole {
    @Override
    public Message validate(AiChatMessage aiChatMessage) {
        if(aiChatMessage.getAiRole().equals(AiRole.ASSISTANT)) return new AssistantMessage(aiChatMessage.getText());
        return null;
    }
}
