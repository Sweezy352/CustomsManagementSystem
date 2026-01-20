package com.example.sweezcustoms.dto.view;

import com.example.sweezcustoms.enums.CustomsStatusEnum;
import com.example.sweezcustoms.enums.DeclarationTypeEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationDtoView {
    private Long id;
    private DeclarationTypeEnum declarationTypeEnum;
    private CustomsStatusEnum status;
    private LocalDateTime verifiedAt;
}
