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
    @NotNull(message = "{product.name.required}")
    @NotBlank(message = "{product.name.required}")
    private String name;
    @NotNull(message = "{product.quantity.required}")
    @NotBlank(message = "{product.quantity.required}")
    private Long quantity;
    @NotNull(message = "{product.weight.required}")
    @NotBlank(message = "{product.weight.required}")
    private BigDecimal weight;
    @NotNull(message = "{product.price.required}")
    @NotBlank(message = "{product.price.required}")
    private BigDecimal pricePerUnit;
    @NotNull(message = "{product.origin.required}")
    @NotBlank(message = "{product.origin.required}")
    private String countryOfOrigin;
}
