package com.example.sweezcustoms.dto.response;

import com.example.sweezcustoms.dto.view.UserDtoView;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import com.example.sweezcustoms.enums.IndividualDocumentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "Полные данные документа физического лица")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndividualDocumentDtoResponse {
    @Schema(description = "ID документа", example = "7")
    private Long id;
    @Schema(description = "Пользователь")
    private UserDtoView userDtoView;
    @Schema(description = "Тип документа", example = "PERSON_PASSPORT")
    private IndividualDocumentType individualDocumentType;
    @Schema(description = "ID файла", example = "15")
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
