package com.example.sweezcustoms.dto.request;

import com.example.sweezcustoms.enums.CompanyDocumentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Schema(description = "Данные документа компании")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDocumentDtoRequest {
    @Schema(description = "Тип документа компании", example = "COMPANY_REG_CERTIFICATE")
    @NotNull(message = "{doc.type.required}")
    @NotBlank(message = "{doc.type.required}")
    private CompanyDocumentType companyDocumentType;
}
