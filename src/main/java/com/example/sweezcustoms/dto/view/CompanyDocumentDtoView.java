package com.example.sweezcustoms.dto.view;

import com.example.sweezcustoms.enums.CompanyDocumentType;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Краткие данные документа компании")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDocumentDtoView {
    @Schema(description = "ID", example = "10")
    private Long id;
    @Schema(description = "Тип документа", example = "COMPANY_REG_CERTIFICATE")
    private CompanyDocumentType companyDocumentType;
    @Schema(description = "Статус", example = "PENDING")
    private CustomsStatusEnum status;
}
