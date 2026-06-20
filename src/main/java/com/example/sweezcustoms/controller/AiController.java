package com.example.sweezcustoms.controller;

import com.example.sweezcustoms.dto.request.AiDtoRequest;
import com.example.sweezcustoms.dto.response.AiDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.BaseChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AiController {
    private final ChatClient chatClient;

    @PostMapping("/ask")
    public String ask(@RequestBody AiDtoRequest aiDtoRequest){
        return chatClient.prompt()
                .user(aiDtoRequest.query())
                .advisors(advisorSpec -> {
                    advisorSpec.param(
                            "chat_memory_conversation_id",
                            aiDtoRequest.id()
                    );
                })
                .call()
                .content();
    }
}
