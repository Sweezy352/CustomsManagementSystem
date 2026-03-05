package com.example.sweezcustoms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "declaration_products")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationProduct extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "declaration_id", referencedColumnName = "id")
    private DeclarationEntity declarationEntity;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "product_description", nullable = false)
    private String productDescription;
    @Column(name = "product_materials", nullable = false)
    private String productMaterials;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tnved_code", referencedColumnName = "id")
    private TnvedCodeEntity tnvedCode;
    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;
    @Column(name = "unit_type", nullable = false)
    private String unitType;
    @Column(name = "weight_netto", nullable = false)
    private BigDecimal weightNetto;
    @Column(name = "weight_brutto", nullable = false)
    private BigDecimal weightBrutto;
    @Column(name = "price_per_unit", nullable = false)
    private BigDecimal pricePerUnit;
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;
    @Column(name = "nds", nullable = false)
    private BigDecimal nds;
    @Column(name = "customs_duty", nullable = false)
    private BigDecimal customsDuty;
    @Column(name = "excise", nullable = false)
    private BigDecimal excise;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }
}
