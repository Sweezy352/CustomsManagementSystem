package com.example.sweezcustoms.dto.response;

import com.example.sweezcustoms.dto.view.DeclarationProductDtoView;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDeclarationDtoResponse {
    private Long id;
    private String declarationType;
    private String currency;
    private BigDecimal currencyRate;
    private LocalDateTime createdAt;
    private LocalDateTime submittedAt;
    private String arrivalWay;
    private String trackingNumber;
    private boolean isPersonal;
    private List<DeclarationProductDto> declarationProductDtos;
}
