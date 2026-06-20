package com.example.sweezcustoms.utils.chainOfResponsibility;

import com.example.sweezcustoms.entity.AiChatMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.content.Content;

@FunctionalInterface
public interface AiChatMessageValidatorRole {
    Message validate(AiChatMessage aiChatMessage);
}
