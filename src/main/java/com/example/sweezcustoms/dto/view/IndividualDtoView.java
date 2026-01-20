package com.example.sweezcustoms.dto.view;

import com.example.sweezcustoms.enums.CustomsStatusEnum;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndividualDtoView{
    private Long id;
    private String fullName;
    private String tin;
    private CustomsStatusEnum status;
}
