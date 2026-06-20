package com.example.sweezcustoms.service;

import com.example.sweezcustoms.entity.AiChat;
import com.example.sweezcustoms.entity.AiChatMessage;

import java.util.List;
import java.util.UUID;

public interface AiChatService {
    AiChat createAiChat(UUID id);
    AiChat findAiChatById(UUID id);
    AiChat findOrCreateAiChat(UUID id);
    AiChatMessage saveChatMessage(AiChatMessage aiChatMessage);
    List<AiChatMessage> getMessages(UUID chatId, long limit);
}
