package com.example.sweezcustoms.dto.request;

import com.example.sweezcustoms.enums.CompanyDocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDocumentDtoRequest {
    @NotNull(message = "Company document type is mandatory")
    @NotBlank(message = "Company document type is mandatory")
    private CompanyDocumentType companyDocumentType;
}
