package com.example.sweezcustoms.dto.response;

import com.example.sweezcustoms.enums.PaymentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentInvoiceDtoResponse {
    private Long id;
    private PaymentStatusEnum paymentStatus;
    private BigDecimal totalInvoiceNds;
    private BigDecimal totalInvoiceCustomsDuty;
    private BigDecimal totalInvoiceExcise;
    private BigDecimal invoiceTotal;
    private LocalDateTime dateCreated;
    private LocalDateTime dateToPay;
    private LocalDateTime datePaid;

}
