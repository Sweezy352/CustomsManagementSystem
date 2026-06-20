package com.example.sweezcustoms.config;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SystemPromptAdvisor implements BaseAdvisor {

    private final String systemPrompt;
    private final int order;

    public SystemPromptAdvisor(String promptClasspath, int order) {
        this.systemPrompt = loadPrompt(promptClasspath);
        this.order = order;
    }

    private String loadPrompt(String path) {
        try {
            return new ClassPathResource(path)
                    .getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось загрузить системный промпт: " + path, e);
        }
    }

    @Override
    public ChatClientRequest before(ChatClientRequest request, AdvisorChain advisorChain) {
        return request.mutate()
                .prompt(request.prompt().augmentSystemMessage(systemPrompt))
                .build();
    }

    @Override
    public ChatClientResponse after(ChatClientResponse response, AdvisorChain advisorChain) {
        return response;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
