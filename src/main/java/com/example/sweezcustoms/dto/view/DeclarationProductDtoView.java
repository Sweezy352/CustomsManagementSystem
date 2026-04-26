package com.example.sweezcustoms.dto.view;

import com.example.sweezcustoms.enums.CustomsStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Schema(description = "Краткие данные товара в декларации")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationProductDtoView {
    @Schema(description = "ID", example = "2001")
    private Long id;
    @Schema(description = "Название", example = "Смартфон Model X")
    private String name;
    @Schema(description = "Количество", example = "100")
    private Long quantity;
    @Schema(description = "Вес (кг)", example = "150.00")
    private BigDecimal weight;
    @Schema(description = "Страна происхождения", example = "CN")
    private String countryOfOrigin;
    @Schema(description = "Статус", example = "PENDING")
    private CustomsStatusEnum status;
}
