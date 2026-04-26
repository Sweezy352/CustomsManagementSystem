package com.example.sweezcustoms.dto.response;

import com.example.sweezcustoms.dto.view.DeclarationProductDtoView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Полные данные декларации физического лица")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDeclarationDtoResponse {
    @Schema(description = "ID декларации", example = "55")
    private Long id;
    @Schema(description = "Тип декларации", example = "IMPORT")
    private String declarationType;
    @Schema(description = "Валюта", example = "USD")
    private String currency;
    @Schema(description = "Курс валюты", example = "87.50")
    private BigDecimal currencyRate;
    @Schema(description = "Дата создания")
    private LocalDateTime createdAt;
    @Schema(description = "Дата подачи")
    private LocalDateTime submittedAt;
    @Schema(description = "Способ доставки", example = "AIR")
    private String arrivalWay;
    @Schema(description = "Номер отслеживания", example = "TRK-2024-XYZ")
    private String trackingNumber;
    @Schema(description = "Личное использование", example = "true")
    private boolean isPersonal;
    @Schema(description = "Товары в декларации")
    private List<DeclarationProductDto> declarationProductDtos;
}
