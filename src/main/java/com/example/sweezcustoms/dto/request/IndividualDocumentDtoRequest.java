package com.example.sweezcustoms.dto.request;

import com.example.sweezcustoms.enums.IndividualDocumentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Schema(description = "Данные документа физического лица")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndividualDocumentDtoRequest {
    @Schema(description = "Тип документа", example = "PERSON_PASSPORT", allowableValues = {"PERSON_PASSPORT", "PERSON_PHOTO_ID"})
    @NotNull(message = "{doc.type.required}")
    @NotBlank(message = "{doc.type.required}")
    private IndividualDocumentType individualDocumentType;
    @Schema(description = "ID файла в хранилище", example = "42")
    private Long fileId;
}
