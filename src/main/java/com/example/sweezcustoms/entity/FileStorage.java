package com.example.sweezcustoms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "file_storage")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileStorage extends BaseEntity{
    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;
    @Column(name = "mime_type", nullable = false)
    private String mimeType;
    @Column(name = "size", nullable = false)
    private Long size;
    @Column(name = "path", nullable = false)
    private String path;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uploaded_by", referencedColumnName = "id")
    private UserEntity uploadedBy;
    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @PrePersist
    public void prePersist() {
        uploadedAt = LocalDateTime.now();
    }
}
