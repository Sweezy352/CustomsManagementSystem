package com.example.sweezcustoms.dto.view;

import com.example.sweezcustoms.enums.CustomsStatusEnum;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDtoView {
    private Long id;
    private String name;
    private String tin;
    private CustomsStatusEnum status;
}
