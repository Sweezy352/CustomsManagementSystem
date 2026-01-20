package com.example.sweezcustoms.dto.view;

import com.example.sweezcustoms.enums.PaymentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentInvoiceDtoView {
    private Long id;
    private PaymentStatusEnum paymentStatus;
    private BigDecimal totalInvoiceNds;
    private BigDecimal totalInvoiceCustomsDuty;
    private BigDecimal totalInvoiceExcise;
    private BigDecimal invoiceTotal;
}
