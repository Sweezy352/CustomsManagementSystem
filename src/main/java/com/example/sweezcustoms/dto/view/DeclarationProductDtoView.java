package com.example.sweezcustoms.dto.view;

import com.example.sweezcustoms.enums.CustomsStatusEnum;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationProductDtoView {
    private Long id;
    private String name;
    private Long quantity;
    private BigDecimal weight;
    private String countryOfOrigin;
    private CustomsStatusEnum status;
}
