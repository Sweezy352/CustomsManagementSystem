package com.example.sweezcustoms.entity;

import com.example.sweezcustoms.enums.CustomsStatusEnum;
import com.example.sweezcustoms.enums.DeclarationDocumentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "declaration_documents")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationDocumentEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "declaration_id", referencedColumnName = "id")
    private DeclarationEntity declarationEntity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private DeclarationProductEntity declarationProductEntity;
    @Column(name = "type", nullable = false)
    private DeclarationDocumentType declarationDocumentType;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private FileStorage file;
    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
    @Column(name = "status")
    private CustomsStatusEnum status;
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "verified_by", referencedColumnName = "id")
    private UserEntity verifiedBy;
    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @PrePersist
    public void prePersist() {
        this.status = CustomsStatusEnum.PENDING;
        this.uploadedAt = LocalDateTime.now();
    }
}