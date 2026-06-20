package com.example.sweezcustoms.entity;

import com.example.sweezcustoms.enums.CustomsStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "companies")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity extends BaseEntity{
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "tin", nullable = false, unique = true)
    private String tin;
    @Column(name = "okpo", nullable = false, unique = true)
    private String okpo;
    @Column(name = "customs_code", nullable = false, unique = true)
    private String customsCode;
    @Column(name = "address", nullable = false)
    private String address;
    @Enumerated(EnumType.STRING)
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
    private List<UserEntity> employees;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "companyEntity")
    private List<CompanyDocumentEntity> companyDocuments;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "companyEntity")
    private List<BranchCompanyEntity> branchCompanyEntities;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private UserEntity owner;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "companyEntity")
    private List<ContractEntity> contractEntities;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "companyEntity")
    private List<CompanyDeclaration> companyDeclarations;

    @PrePersist
    public void prePersist() {
        status = CustomsStatusEnum.PENDING;
        createdAt = LocalDateTime.now();
    }

}
