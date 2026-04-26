package com.example.sweezcustoms.dto.response;

import com.example.sweezcustoms.dto.view.UserDtoView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Полные данные кода ТН ВЭД")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TnvedCodeDtoResponse {
    @Schema(description = "ID", example = "1")
    private Long id;
    @Schema(description = "Код ТН ВЭД", example = "8517120000")
    private String code;
    @Schema(description = "Описание", example = "Телефоны сотовой связи")
    private String description;
    @Schema(description = "Ставка таможенной пошлины (%)", example = "5.00")
    private BigDecimal defaultCustomsDutyRate;
    @Schema(description = "Ставка акциза (%)", example = "0.00")
    private BigDecimal defaultExciseRate;
    @Schema(description = "Ставка НДС (%)", example = "12.00")
    private BigDecimal defaultNdsRate;
    @Schema(description = "Пользователь, создавший запись")
    private UserDtoView userDtoView;
    @Schema(description = "Дата создания")
    private LocalDateTime dateCreated;
}
