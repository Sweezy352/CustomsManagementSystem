package com.example.sweezcustoms.dto.request;

import com.example.sweezcustoms.enums.DeclarationDocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationProductDocumentDtoRequest {
    @NotNull(message = "Document type is mandatory")
    @NotBlank(message = "Document type is mandatory")
    private DeclarationDocumentType documentType;
}
