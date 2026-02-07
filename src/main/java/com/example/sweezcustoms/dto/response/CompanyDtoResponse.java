package com.example.sweezcustoms.dto.response;

import com.example.sweezcustoms.dto.view.CompanyDocumentDtoView;
import com.example.sweezcustoms.dto.view.DeclarationDtoView;
import com.example.sweezcustoms.dto.view.UserDtoView;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDtoResponse {
    private Long id;
    private String name;
    private String tin;
    private String address;
    private CustomsStatusEnum status;
    private LocalDateTime createdAt;
    private UserDtoView verifiedBy;
    private LocalDateTime verifiedAt;
    private List<UserDtoView> employees;
    private List<CompanyDocumentDtoView> companyDocumentDtoViews;
    private List<DeclarationDtoView> declarationDtoViews;
    private UserDtoView owner;
}
