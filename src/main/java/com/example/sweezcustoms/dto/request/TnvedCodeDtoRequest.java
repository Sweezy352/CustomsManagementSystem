package com.example.sweezcustoms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Schema(description = "Данные кода ТН ВЭД")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TnvedCodeDtoRequest {
    @Schema(description = "Код ТН ВЭД (10 цифр)", example = "8517120000")
    @NotNull(message = "{tnved.code.required}")
    @NotBlank(message = "{tnved.code.required}")
    private String code;
    @Schema(description = "Описание товарной позиции", example = "Телефоны сотовой связи")
    @NotNull(message = "{tnved.description.required}")
    @NotBlank(message = "{tnved.description.required}")
    private String description;
    @Schema(description = "Ставка таможенной пошлины (%)", example = "5.00")
    @NotNull(message = "{tnved.duty.required}")
    private BigDecimal defaultCustomsDutyRate;
    @Schema(description = "Ставка акциза (%)", example = "0.00")
    @NotNull(message = "{tnved.excise.required}")
    private BigDecimal defaultExciseRate;
    @Schema(description = "Ставка НДС (%)", example = "12.00")
    @NotNull(message = "{tnved.nds.required}")
    private BigDecimal defaultNdsRate;
}
