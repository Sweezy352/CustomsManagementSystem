package com.example.sweezcustoms.dto.response;

import com.example.sweezcustoms.dto.view.CompanyDtoView;
import com.example.sweezcustoms.dto.view.UserDtoView;
import com.example.sweezcustoms.enums.CompanyDocumentType;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "Полные данные документа компании")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDocumentDtoResponse {
    @Schema(description = "ID документа", example = "10")
    private Long id;
    @Schema(description = "Компания")
    private CompanyDtoView companyDtoView;
    @Schema(description = "Тип документа", example = "COMPANY_REG_CERTIFICATE")
    private CompanyDocumentType companyDocumentType;
    @Schema(description = "ID файла", example = "42")
    private Long fileId;
    @Schema(description = "Дата загрузки")
    private LocalDateTime uploadedAt;
    @Schema(description = "Статус верификации", example = "PENDING")
    private CustomsStatusEnum status;
    @Schema(description = "Инспектор, подтвердивший документ")
    private UserDtoView verifiedBy;
    @Schema(description = "Дата верификации")
    private LocalDateTime verifiedAt;
}
