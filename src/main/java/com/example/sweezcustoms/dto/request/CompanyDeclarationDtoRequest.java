package com.example.sweezcustoms.dto.request;

import com.example.sweezcustoms.enums.DeclarationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Данные для создания декларации компании")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDeclarationDtoRequest {
    @Schema(description = "Тип декларации", example = "IMPORT", allowableValues = {"IMPORT", "EXPORT"})
    @NotNull(message = "{declaration.type.required}")
    @NotBlank(message = "{declaration.type.required}")
    private DeclarationTypeEnum declarationType;
    @Schema(description = "Валюта расчёта", example = "USD")
    private String currency;
    @Schema(description = "Номер инвойса", example = "INV-2024-001")
    private String invoiceNumber;
    @Schema(description = "Дата инвойса", example = "2024-01-15")
    private String invoiceDate;
    @Schema(description = "Код Incoterms", example = "FOB")
    private String incotermsCode;
    @Schema(description = "Место поставки по Incoterms", example = "Shanghai")
    private String incotermsPlace;
    @Schema(description = "Тип транспорта", example = "SEA")
    private String transportType;
    @Schema(description = "Номер транспортного средства", example = "COSCO-12345")
    private String transportId;
}
