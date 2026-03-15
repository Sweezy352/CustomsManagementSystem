package com.example.sweezcustoms.dto.request;

import com.example.sweezcustoms.enums.DeclarationTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDeclarationDtoRequest {
    @NotNull(message = "{declaration.type.required}")
    @NotBlank(message = "{declaration.type.required}")
    private DeclarationTypeEnum declarationType;
    private String currency;
    private String invoiceNumber;
    private String invoiceDate;
    private String incotermsCode;
    private String incotermsPlace;
    private String transportType;
    private String transportId;
}
