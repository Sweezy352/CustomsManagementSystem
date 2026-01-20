package com.example.sweezcustoms.dto.view;

import com.example.sweezcustoms.enums.CompanyDocumentType;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDocumentDtoView {
    private Long id;
    private CompanyDocumentType companyDocumentType;
    private CustomsStatusEnum status;
}
