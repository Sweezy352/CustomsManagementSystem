package com.example.sweezcustoms.entity;

import com.example.sweezcustoms.enums.AiRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.TimeZoneStorage;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "ai_chat_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "text", nullable = false)
    private String text;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    private AiRole aiRole;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ai_chat_id", referencedColumnName = "id")
    private AiChat aiChat;

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }
}
