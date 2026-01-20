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
public class DeclarationDtoRequest {
    @NotNull(message = "Declaration type is mandatory")
    @NotBlank(message = "Declaration type is mandatory")
    private DeclarationTypeEnum declarationTypeEnum;
}
