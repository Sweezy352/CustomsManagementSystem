package com.example.sweezcustoms.dto.request;

import com.example.sweezcustoms.enums.IndividualDocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndividualDocumentDtoRequest {
    @NotNull(message = "Document type is mandatory")
    @NotBlank(message = "Document type is mandatory")
    private IndividualDocumentType individualDocumentType;
    private Long fileId;
}
