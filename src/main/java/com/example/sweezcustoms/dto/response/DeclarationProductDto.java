package com.example.sweezcustoms.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Товар в декларации")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationProductDto {
    @Schema(description = "ID товара", example = "2001")
    private Long id;
    @Schema(description = "Название", example = "Смартфон Model X")
    private String productName;
    @Schema(description = "Описание")
    private String productDescription;
    @Schema(description = "Материалы")
    private String productMaterials;
    @Schema(description = "Код ТН ВЭД", example = "8517120000")
    private String tnvedCode;
    @Schema(description = "Количество", example = "100")
    private BigDecimal quantity;
    @Schema(description = "Единица измерения", example = "PCS")
    private String unitType;
    @Schema(description = "Вес нетто (кг)", example = "150.00")
    private BigDecimal weightNetto;
    @Schema(description = "Вес брутто (кг)", example = "165.00")
    private BigDecimal weightBrutto;
    @Schema(description = "Цена за единицу", example = "250.00")
    private BigDecimal pricePerUnit;
    @Schema(description = "Общая стоимость", example = "25000.00")
    private BigDecimal totalPrice;
    @Schema(description = "Рассчитанный НДС", example = "3000.00")
    private BigDecimal nds;
    @Schema(description = "Рассчитанная таможенная пошлина", example = "1250.00")
    private BigDecimal customsDuty;
    @Schema(description = "Рассчитанный акциз", example = "0.00")
    private BigDecimal excise;
    @Schema(description = "Дата создания")
    private LocalDateTime createdAt;
}
