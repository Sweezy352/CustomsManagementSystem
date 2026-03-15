package com.example.sweezcustoms.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationProductDto {
    private Long id;
    private String productName;
    private String productDescription;
    private String productMaterials;
    private String tnvedCode;
    private BigDecimal quantity;
    private String unitType;
    private BigDecimal weightNetto;
    private BigDecimal weightBrutto;
    private BigDecimal pricePerUnit;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
}
