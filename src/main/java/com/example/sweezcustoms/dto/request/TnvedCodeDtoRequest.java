package com.example.sweezcustoms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TnvedCodeDtoRequest {
    @NotNull(message = "{tnved.code.required}")
    @NotBlank(message = "{tnved.code.required}")
    private String code;
    @NotNull(message = "{tnved.description.required}")
    @NotBlank(message = "{tnved.description.required}")
    private String description;
    @NotNull(message = "{tnved.duty.required}")
    @NotBlank(message = "{tnved.duty.required}")
    private BigDecimal defaultCustomsDutyRate;
    @NotNull(message = "{tnved.excise.required}")
    @NotBlank(message = "{tnved.excise.required}")
    private BigDecimal defaultExciseRate;
    @NotNull(message = "{tnved.nds.required}")
    @NotBlank(message = "{tnved.nds.required}")
    private BigDecimal defaultNdsRate;
}
