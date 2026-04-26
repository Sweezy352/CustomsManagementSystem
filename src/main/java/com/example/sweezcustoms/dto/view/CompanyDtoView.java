package com.example.sweezcustoms.dto.view;

import com.example.sweezcustoms.enums.CustomsStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Краткие данные компании")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDtoView {
    @Schema(description = "ID", example = "1")
    private Long id;
    @Schema(description = "Название", example = "Bishkek Style LLC")
    private String name;
    @Schema(description = "ИНН", example = "12345678901")
    private String tin;
    @Schema(description = "Статус", example = "PENDING")
    private CustomsStatusEnum status;
    @Schema(description = "Владелец")
    private UserDtoView owner;
}
