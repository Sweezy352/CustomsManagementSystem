package com.example.sweezcustoms.dto.response;

import com.example.sweezcustoms.dto.view.CompanyDeclarationDtoView;
import com.example.sweezcustoms.dto.view.CompanyDocumentDtoView;
import com.example.sweezcustoms.dto.view.UserDtoView;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Полные данные компании")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDtoResponse {
    @Schema(description = "ID компании", example = "1")
    private Long id;
    @Schema(description = "Название", example = "Bishkek Style LLC")
    private String name;
    @Schema(description = "ИНН", example = "12345678901")
    private String tin;
    @Schema(description = "ОКПО", example = "98765432")
    private String okpo;
    @Schema(description = "Таможенный код", example = "TC-001")
    private String customsCode;
    @Schema(description = "Адрес", example = "Бишкек, ул. Молодая Гвардия, 1")
    private String address;
    @Schema(description = "Статус верификации", example = "PENDING")
    private CustomsStatusEnum status;
    @Schema(description = "Дата регистрации")
    private LocalDateTime createdAt;
    @Schema(description = "Инспектор, подтвердивший компанию")
    private UserDtoView verifiedBy;
    @Schema(description = "Дата верификации")
    private LocalDateTime verifiedAt;
    @Schema(description = "Список сотрудников")
    private List<UserDtoView> employees;
    @Schema(description = "Документы компании")
    private List<CompanyDocumentDtoView> companyDocumentDtoViews;
    @Schema(description = "Декларации компании")
    private List<CompanyDeclarationDtoView> declarationDtoViews;
    @Schema(description = "Владелец компании")
    private UserDtoView owner;
}
