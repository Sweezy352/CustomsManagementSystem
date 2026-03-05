package com.example.sweezcustoms.dto.request;

import com.example.sweezcustoms.enums.DeclarationTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDeclarationDtoRequest {
    @NotNull(message = "{declaration.type.required}")
    @NotBlank(message = "{declaration.type.required}")
    private DeclarationTypeEnum declarationTypeEnum;
    private String currency;
    private String arrivalWay;
    private String trackingNumber;
    private boolean isPersonal;
}
