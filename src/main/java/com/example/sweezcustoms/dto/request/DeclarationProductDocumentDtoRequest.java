package com.example.sweezcustoms.dto.request;

import com.example.sweezcustoms.enums.DeclarationDocumentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Schema(description = "Данные документа товара в декларации")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationProductDocumentDtoRequest {
    @Schema(description = "Тип документа", example = "PRODUCT_CERTIFICATE_ORIGIN")
    @NotNull(message = "{doc.type.required}")
    @NotBlank(message = "{doc.type.required}")
    private DeclarationDocumentType documentType;
}
