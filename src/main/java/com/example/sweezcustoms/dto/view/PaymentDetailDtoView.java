package com.example.sweezcustoms.dto.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentDetailDtoView {
    private Long id;
    private String description;
    private BigDecimal amount;
    private DeclarationProductDtoView sourceProduct;
}
