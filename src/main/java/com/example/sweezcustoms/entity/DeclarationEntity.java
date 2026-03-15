package com.example.sweezcustoms.entity;

import com.example.sweezcustoms.enums.CurrencyEnum;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import com.example.sweezcustoms.enums.DeclarationDocumentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "declarations")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class DeclarationEntity extends BaseEntity{
    @Column(name = "document_type", nullable = false)
    protected DeclarationDocumentType documentType;
    @Column(name = "status")
    protected CustomsStatusEnum status;
    @Column(name = "created_at")
    protected LocalDateTime createdAt;
    @Column(name = "submitted_at")
    protected LocalDateTime submittedAt;
    @Column(name = "currency", nullable = false)
    protected CurrencyEnum currency;
    @Column(name = "currency_rate", nullable = false)
    protected BigDecimal currencyRate;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "declarationEntity")
    protected List<DeclarationProduct> declarationProducts;
    @Column(name = "file_name", unique = true)
    protected String fileName;


    @PrePersist
    public void prePersist(){
        status = CustomsStatusEnum.DRAFT;
        createdAt = LocalDateTime.now();
    }
}
