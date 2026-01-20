package com.example.sweezcustoms.entity;

import com.example.sweezcustoms.enums.CompanyDocumentType;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "company_documents")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDocumentEntity extends BaseEntity{
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private CompanyEntity companyEntity;
    @Column(name = "document_type", nullable = false)
    private CompanyDocumentType companyDocumentType;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", referencedColumnName = "id", unique = true)
    private FileStorage file;
    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
    @Column(name = "status", nullable = false)
    private CustomsStatusEnum status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verified_by", referencedColumnName = "id")
    private UserEntity verifiedBy;
    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @PrePersist
    public void prePersist() {
        status = CustomsStatusEnum.PENDING;
        uploadedAt = LocalDateTime.now();
    }
}
