package com.example.sweezcustoms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tnved_codes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TnvedCodeEntity extends BaseEntity{
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "default_customs_duty_rate", nullable = false)
    private BigDecimal defaultCustomsDutyRate;
    @Column(name = "default_excise_rate", nullable = false)
    private BigDecimal defaultExciseRate;
    @Column(name = "default_nds_rate", nullable = false)
    private BigDecimal defaultNdsRate;
}
