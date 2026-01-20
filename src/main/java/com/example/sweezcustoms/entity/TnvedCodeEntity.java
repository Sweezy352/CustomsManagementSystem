package com.example.sweezcustoms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tnved_codes")
@Getter
@Setter
@SuperBuilder
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;
    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @PrePersist
    public void prePersist() {
        dateCreated = LocalDateTime.now();
    }
}
