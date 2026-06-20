package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.entity.AiChat;
import com.example.sweezcustoms.entity.AiChatMessage;
import com.example.sweezcustoms.repository.AiChatMessageRepository;
import com.example.sweezcustoms.repository.AiChatRepository;
import com.example.sweezcustoms.service.AiChatService;
import com.example.sweezcustoms.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AiChatServiceImpl implements AiChatService {
    private final AiChatMessageRepository aiChatMessageRepository;
    private final AuthService authService;
    private final AiChatRepository aiChatRepository;

    @Override
    public AiChat createAiChat(UUID id) {
        AiChat aiChat = AiChat.builder()
                .id(id == null ? UUID.randomUUID() : id)
                .build();
        return aiChatRepository.save(aiChat);
    }

    @Override
    public AiChat findAiChatById(UUID id) {
        return aiChatRepository.findById(id).orElseGet(() -> createAiChat(id));
    }

    @Override
    @Transactional
    public AiChat findOrCreateAiChat(UUID id) {
        return aiChatRepository.findByIdWithLock(id)
                .orElseGet(() -> createAiChat(id));
    }

    @Override
    @Transactional
    public AiChatMessage saveChatMessage(AiChatMessage aiChatMessage) {
        return aiChatMessageRepository.save(aiChatMessage);
    }

    @Override
    public List<AiChatMessage> getMessages(UUID chatId, long limit) {
        List<AiChatMessage> messages = aiChatMessageRepository.findByChatId(chatId);
        return messages.stream()
                .skip(Math.max(0, messages.size() - limit))
                .toList();
    }
}
