package com.example.sweezcustoms.dto.response;

import com.example.sweezcustoms.dto.view.DeclarationDtoView;
import com.example.sweezcustoms.dto.view.IndividualDocumentDtoView;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndividualDtoResponse {
    private Long id;
    private String fullName;
    private LocalDate birthDate;
    private String address;
    private String tin;
    private LocalDateTime createdAt;
    private CustomsStatusEnum status;
    private List<DeclarationDtoView> declarationDtoViews;
    private List<IndividualDocumentDtoView> individualDocumentDtoViews;
}
