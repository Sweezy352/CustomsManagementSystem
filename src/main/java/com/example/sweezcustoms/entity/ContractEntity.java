package com.example.sweezcustoms.entity;

import com.example.sweezcustoms.enums.ContractEnums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "contracts")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ContractEntity extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private CompanyEntity companyEntity;
    @Column(name = "contract_numbers", nullable = false)
    private String contractNumbers;
    @Column(name = "contract_date", nullable = false, columnDefinition = "DATE")
    private LocalDate contractDate;
    @Column(name = "contract_expiry", nullable = false, columnDefinition = "DATE")
    private LocalDate contractExpiry;
    @Column(name = "currency_code", nullable = false)
    private String currencyCode;
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ContractEnums status;
    @Column(name = "file_name", nullable = false, unique = true)
    private String fileName;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
        status = ContractEnums.ACTIVE;
    }
}
