package com.example.sweezcustoms.utils.chainOfResponsibility.impl;

import com.example.sweezcustoms.entity.AiChatMessage;
import com.example.sweezcustoms.enums.AiRole;
import com.example.sweezcustoms.utils.chainOfResponsibility.AiChatMessageValidatorRole;
import org.springframework.ai.chat.messages.Message;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Component;

@Component
public class AiChatMessageValidatorUserRole implements AiChatMessageValidatorRole {
    @Override
    public Message validate(AiChatMessage aiChatMessage) {
        if(aiChatMessage.getAiRole().equals(AiRole.USER)) return new UserMessage(aiChatMessage.getText());
        return null;
    }
}
