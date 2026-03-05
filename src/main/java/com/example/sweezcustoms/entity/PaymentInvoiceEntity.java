package com.example.sweezcustoms.entity;

import com.example.sweezcustoms.enums.PaymentStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "payment_invoices")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInvoiceEntity extends BaseEntity {
    @Column(name = "status")
    private PaymentStatusEnum paymentStatus;
    @Column(name = "total_invoice_nds", nullable = false)
    private BigDecimal totalInvoiceNds;
    @Column(name = "total_invoice_customs_duty", nullable = false)
    private BigDecimal totalInvoiceCustomsDuty;
    @Column(name = "total_invoice_excise", nullable = false)
    private BigDecimal totalInvoiceExcise;
    @Column(name = "invoice_total", nullable = false)
    private BigDecimal invoiceTotal;
    @Column(name = "date_created")
    private LocalDateTime dateCreated;
    @Column(name = "date_to_pay")
    private LocalDateTime dateToPay;
    @Column(name = "date_paid")
    private LocalDateTime datePaid;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "declaration_id", referencedColumnName = "id")
    private DeclarationEntity declarationEntity;

    @PrePersist
    public void prePersist() {
        this.paymentStatus = PaymentStatusEnum.ISSUED;
        this.dateCreated = LocalDateTime.now();
        this.dateToPay = LocalDateTime.now().plusDays(15);
    }
}
