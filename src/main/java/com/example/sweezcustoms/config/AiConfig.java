package com.example.sweezcustoms.config;

import com.example.sweezcustoms.service.AiChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AiConfig {
    private final AiChatService aiChatService;
    @Value("${max.messages}")
    private int maxMessages;

    @Bean
    public JdbcChatMemoryRepository jdbcChatMemoryRepository(JdbcTemplate jdbcTemplate) {
        return JdbcChatMemoryRepository.builder()
                .jdbcTemplate(jdbcTemplate)
                .build();
    }

    @Bean
    public ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository){
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .maxMessages(maxMessages)
                .build();
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory){
        return builder
                .defaultAdvisors(
                        new SystemPromptAdvisor("docsai/promptAiProject.md", 0),
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .defaultOptions(
                        MistralAiChatOptions.builder()
                                .temperature(0.3)
                                .build()
                ).build();
    }
//
//    private Advisor addChatMemory(int order) {
//        return MessageChatMemoryAdvisor.builder(createCustomPostgresChatMemory())
//                .order(order)
//                .build();
//    }
//
//    private ChatMemory createCustomPostgresChatMemory() {
//        return CustomPostgresChatMemory.builder()
//                .aiChatService(aiChatService)
//                .maxMessages(maxMessages)
//                .build();
//    }
}
