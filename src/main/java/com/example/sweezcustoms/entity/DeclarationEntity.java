package com.example.sweezcustoms.entity;

import com.example.sweezcustoms.enums.CustomsStatusEnum;
import com.example.sweezcustoms.enums.DeclarationTypeEnum;
import com.example.sweezcustoms.exceptions.BothFieldNullPointerException;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "declarations")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationEntity extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", referencedColumnName = "id")
    private Participant participant;
    @Column(name = "type", nullable = false)
    private DeclarationTypeEnum type;
    @Column(name = "status")
    private CustomsStatusEnum status;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verified_by", referencedColumnName = "id")
    private UserEntity verifiedBy;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "declarationEntity")
    private List<DeclarationProductEntity> declarationProducts;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "declarationEntity")
    private List<PaymentInvoiceEntity> paymentInvoices;

    @PrePersist
    public void prePersist() {
        status = CustomsStatusEnum.DRAFT;
        createdAt = LocalDateTime.now();
    }
}
