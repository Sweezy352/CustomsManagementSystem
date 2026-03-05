package com.example.sweezcustoms.dto.view;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDeclarationDtoView {
    private Long id;
    private String declarationType;
    private String currency;
    private BigDecimal currencyRate;
    private LocalDateTime createdAt;
    private LocalDateTime submittedAt;
    private String invoiceNumber;
    private String invoiceDate;
    private String incotermsCode;
    private String incotermsPlace;
    private String transportType;
    private String transportId;
}
