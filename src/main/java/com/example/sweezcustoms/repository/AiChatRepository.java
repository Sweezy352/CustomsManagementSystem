package com.example.sweezcustoms.repository;

import com.example.sweezcustoms.entity.AiChat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AiChatRepository extends JpaRepository<AiChat, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM AiChat c WHERE c.id = :id")
    Optional<AiChat> findByIdWithLock(UUID id);
}
