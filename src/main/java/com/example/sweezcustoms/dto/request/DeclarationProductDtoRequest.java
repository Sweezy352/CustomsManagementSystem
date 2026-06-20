package com.example.sweezcustoms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Данные товара в декларации")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeclarationProductDtoRequest {
    @Schema(description = "Название товара", example = "Смартфон Model X")
    @NotNull(message = "{product.name.required}")
    @NotBlank(message = "{product.name.required}")
    private String productName;
    @Schema(description = "Описание товара", example = "Андроид-смартфон, 6.5 дюйм")
    private String productDescription;
    @Schema(description = "Материалы изготовления", example = "Пластик, стекло, металл")
    private String productMaterials;
    @Schema(description = "Код ТН ВЭД", example = "8517120000")
    private String tnvedCode;
    @Schema(description = "Количество", example = "100")
    @NotNull(message = "{product.quantity.required}")
    @DecimalMin(value = "0", inclusive = false, message = "{product.quantity.min}")
    private BigDecimal quantity;
    @Schema(description = "Единица измерения", example = "PCS")
    private String unitType;
    @Schema(description = "Вес нетто (кг)", example = "150.00")
    @DecimalMin(value = "0", inclusive = false, message = "{product.weightNetto.min}")
    private BigDecimal weightNetto;
    @Schema(description = "Вес брутто (кг)", example = "165.00")
    @DecimalMin(value = "0", inclusive = false, message = "{product.weightBrutto.min}")
    private BigDecimal weightBrutto;
    @Schema(description = "Цена за единицу", example = "250.00")
    @DecimalMin(value = "0", inclusive = false, message = "{product.pricePerUnit.min}")
    private BigDecimal pricePerUnit;
    @Schema(description = "Общая стоимость", example = "25000.00")
    @DecimalMin(value = "0", inclusive = false, message = "{product.totalPrice.min}")
    private BigDecimal totalPrice;
}
