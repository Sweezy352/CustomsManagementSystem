package com.example.sweezcustoms.dto.response;

import com.example.sweezcustoms.dto.view.CompanyDtoView;
import com.example.sweezcustoms.dto.view.UserDtoView;
import com.example.sweezcustoms.enums.CompanyDocumentType;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDocumentDtoResponse {
    private Long id;
    private CompanyDtoView companyDtoView;
    private CompanyDocumentType companyDocumentType;
    private Long fileId;
    private LocalDateTime uploadedAt;
    private CustomsStatusEnum status;
    private UserDtoView verifiedBy;
    private LocalDateTime verifiedAt;
}
