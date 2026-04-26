package com.example.sweezcustoms.dto.response;

import com.example.sweezcustoms.enums.PaymentStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Платёжный счёт по декларации")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentInvoiceDtoResponse {
    @Schema(description = "ID счёта", example = "1")
    private Long id;
    @Schema(description = "Статус оплаты", example = "PENDING")
    private PaymentStatusEnum paymentStatus;
    @Schema(description = "Сумма НДС", example = "3000.00")
    private BigDecimal totalInvoiceNds;
    @Schema(description = "Сумма таможенной пошлины", example = "1250.00")
    private BigDecimal totalInvoiceCustomsDuty;
    @Schema(description = "Сумма акциза", example = "0.00")
    private BigDecimal totalInvoiceExcise;
    @Schema(description = "Итоговая сумма", example = "4250.00")
    private BigDecimal invoiceTotal;
    @Schema(description = "Дата создания")
    private LocalDateTime dateCreated;
    @Schema(description = "Срок оплаты")
    private LocalDateTime dateToPay;
    @Schema(description = "Дата оплаты")
    private LocalDateTime datePaid;
}
