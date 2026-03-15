package com.example.sweezcustoms.dto.view;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDeclarationDtoView {
    private Long id;
    private String declarationType;
    private String currency;
    private BigDecimal currencyRate;
    private LocalDateTime createdAt;
    private LocalDateTime submittedAt;
    private String arrivalWay;
    private String trackingNumber;
    private boolean isPersonal;
}
