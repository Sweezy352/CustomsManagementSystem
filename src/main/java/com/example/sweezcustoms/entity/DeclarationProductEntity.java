package com.example.sweezcustoms.entity;

import com.example.sweezcustoms.enums.CustomsStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "declaration_products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationProductEntity extends BaseEntity{
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "declaration_id", referencedColumnName = "id")
    private DeclarationEntity declarationEntity;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tnved_code", referencedColumnName = "id")
    private TnvedCodeEntity tnvedCodeEntity;
    @Column(name = "quantity", nullable = false)
    private Long quantity;
    @Column(name = "weight", nullable = false)
    private BigDecimal weight;
    @Column(name = "price_per_unit", nullable = false)
    private BigDecimal pricePerUnit;
    @Column(name = "default_nds_rate", nullable = false)
    private BigDecimal defaultNdsRate;
    @Column(name = "default_customs_duty_rate", nullable = false)
    private BigDecimal defaultCustomsDutyRate;
    @Column(name = "default_excise_rate", nullable = false)
    private BigDecimal defaultExciseRate;
    @Column(name = "country_of_origin", nullable = false)
    private String countryOfOrigin;
    @Column(name = "status")
    private CustomsStatusEnum status;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "verified_by", referencedColumnName = "id")
    private UserEntity verifiedBy;
    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @PrePersist
    public void prePersist() {
        status = CustomsStatusEnum.DRAFT;
        createdAt = LocalDateTime.now();

    }
}
