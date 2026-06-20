package com.example.sweezcustoms.repository;

import com.example.sweezcustoms.entity.AiChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AiChatMessageRepository extends JpaRepository<AiChatMessage, UUID> {
    @Query("SELECT m FROM AiChatMessage m WHERE m.aiChat.id = :chatId ORDER BY m.createdAt ASC")
    List<AiChatMessage> findByChatId(@Param("chatId") UUID chatId);
}
