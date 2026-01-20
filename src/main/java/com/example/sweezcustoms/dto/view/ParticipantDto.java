package com.example.sweezcustoms.dto.view;

import com.example.sweezcustoms.enums.CustomsStatusEnum;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ParticipantDto {
    private Long id;
    private String type;

    private CustomsStatusEnum status;
    private String tin;
}
