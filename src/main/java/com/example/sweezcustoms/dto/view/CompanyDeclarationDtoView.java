package com.example.sweezcustoms.dto.view;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Краткие данные декларации компании")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDeclarationDtoView {
    @Schema(description = "ID", example = "101")
    private Long id;
    @Schema(description = "Тип", example = "IMPORT")
    private String declarationType;
    @Schema(description = "Валюта", example = "USD")
    private String currency;
    @Schema(description = "Курс", example = "87.50")
    private BigDecimal currencyRate;
    @Schema(description = "Дата создания")
    private LocalDateTime createdAt;
    @Schema(description = "Дата подачи")
    private LocalDateTime submittedAt;
    @Schema(description = "Номер инвойса", example = "INV-2024-001")
    private String invoiceNumber;
    @Schema(description = "Дата инвойса", example = "2024-01-15")
    private String invoiceDate;
    @Schema(description = "Код Incoterms", example = "FOB")
    private String incotermsCode;
    @Schema(description = "Место Incoterms", example = "Shanghai")
    private String incotermsPlace;
    @Schema(description = "Тип транспорта", example = "SEA")
    private String transportType;
    @Schema(description = "Номер транспортного средства", example = "COSCO-12345")
    private String transportId;
}
