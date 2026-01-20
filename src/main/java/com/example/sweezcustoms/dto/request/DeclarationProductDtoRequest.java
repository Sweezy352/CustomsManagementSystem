package com.example.sweezcustoms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeclarationProductDtoRequest {
    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull(message = "Quantity is mandatory")
    @NotBlank(message = "Quantity is mandatory")
    private Long quantity;
    @NotNull(message = "Weight is mandatory")
    @NotBlank(message = "Weight is mandatory")
    private BigDecimal weight;
    @NotNull(message = "Price per unit is mandatory")
    @NotBlank(message = "Price per unit is mandatory")
    private BigDecimal pricePerUnit;
    @NotNull(message = "Country of origin is mandatory")
    @NotBlank(message = "Country of origin is mandatory")
    private String countryOfOrigin;
}
