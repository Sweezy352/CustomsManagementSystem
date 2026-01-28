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
public class CompanyEntity extends Participant{
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
    private List<UserEntity> employees;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "companyEntity")
    private List<CompanyDocumentEntity> companyDocuments;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "companyEntity")
    private List<BranchCompanyEntity> branchCompanyEntities;

    @PrePersist
    public void prePersist() {
        status = CustomsStatusEnum.PENDING;
        createdAt = LocalDateTime.now();
    }

    @Override
    public String getType() {
        return name;
    }

    @Override
    public String getTin() {
        return tin;
    }

    @Override
    public CustomsStatusEnum getStatus() {
        return status;
    }
}
