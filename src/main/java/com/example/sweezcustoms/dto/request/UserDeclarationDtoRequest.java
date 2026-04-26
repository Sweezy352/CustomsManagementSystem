package com.example.sweezcustoms.dto.request;

import com.example.sweezcustoms.enums.DeclarationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Schema(description = "Данные для создания декларации физического лица")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDeclarationDtoRequest {
    @Schema(description = "Тип декларации", example = "IMPORT", allowableValues = {"IMPORT", "EXPORT"})
    @NotNull(message = "{declaration.type.required}")
    @NotBlank(message = "{declaration.type.required}")
    private DeclarationTypeEnum declarationTypeEnum;
    @Schema(description = "Валюта", example = "USD")
    private String currency;
    @Schema(description = "Способ доставки", example = "AIR")
    private String arrivalWay;
    @Schema(description = "Номер отслеживания", example = "TRK-2024-XYZ")
    private String trackingNumber;
    @Schema(description = "Личное использование (не для перепродажи)", example = "true")
    private boolean isPersonal;
}
