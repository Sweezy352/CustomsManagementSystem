package com.example.sweezcustoms.dto.view;

import com.example.sweezcustoms.enums.CustomsStatusEnum;
import com.example.sweezcustoms.enums.IndividualDocumentType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndividualDocumentDtoView {
    private Long id;
    private IndividualDocumentType individualDocumentType;
    private CustomsStatusEnum status;
}
