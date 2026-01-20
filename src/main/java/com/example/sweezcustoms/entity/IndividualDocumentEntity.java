package com.example.sweezcustoms.entity;

import com.example.sweezcustoms.enums.CustomsStatusEnum;
import com.example.sweezcustoms.enums.IndividualDocumentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "individual_documents")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class IndividualDocumentEntity extends BaseEntity{
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "individual_id", referencedColumnName = "id")
    private IndividualEntity individualEntity;
    @Column(name = "document_type", nullable = false)
    private IndividualDocumentType individualDocumentType;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id", referencedColumnName = "id", unique = true)
    private FileStorage file;
    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
    @Column(name = "status", nullable = false)
    private CustomsStatusEnum status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "verified_by", referencedColumnName = "id")
    private UserEntity verifiedBy;
    @Column(name = "verifiedAt")
    private LocalDateTime verifiedAt;

    @PrePersist
    public void prePersist() {
        uploadedAt = LocalDateTime.now();
    }
}
