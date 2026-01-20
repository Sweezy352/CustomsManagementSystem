package com.example.sweezcustoms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reported_caches")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ReportedCacheEntity extends BaseEntity{
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private CompanyEntity companyEntity;
    @Column(name = "period_start", nullable = false)
    private LocalDateTime periodStart;
    @Column(name = "period_end", nullable = false)
    private LocalDateTime periodEnd;
    @Column(name = "total_import", nullable = false)
    private Long totalImport;
    @Column(name = "total_export", nullable = false)
    private Long totalExport;
    @Column(name = "total_taxes_paid", nullable = false)
    private BigDecimal totalTaxesPaid;
    @Column(name = "total_rejected", nullable = false)
    private Long totalRejected;
    @Column(name = "generated_at")
    private LocalDateTime generatedAt;

    @PrePersist
    public void prePersist() {
        generatedAt = LocalDateTime.now();
    }
}
