package com.example.sweezcustoms.dto.view;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchCompanyDtoView {
    private Long id;
    private String branchName;
    private String phone;
    private String address;
}
