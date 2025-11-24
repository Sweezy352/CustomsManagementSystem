package com.example.sweezcustoms.entity;

import com.example.sweezcustoms.enums.CustomsStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "companies")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity extends BaseEntity{
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "tin", nullable = false, unique = true)
    private String tin;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "status")
    private CustomsStatusEnum status;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verified_by", referencedColumnName = "id")
    private UserEntity verifiedBy;
    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "companyEntity")
    private List<UserEntity> usersCompany;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "companyEntity")
    private List<CompanyDocumentEntity> companyDocuments;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "companyEntity")
    private List<DeclarationEntity> declarationEntities;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "companyEntity")
    private List<PaymentInvoiceEntity> paymentInvoices;

    @PrePersist
    public void prePersist() {
        status = CustomsStatusEnum.PENDING;
        createdAt = LocalDateTime.now();
    }
}
