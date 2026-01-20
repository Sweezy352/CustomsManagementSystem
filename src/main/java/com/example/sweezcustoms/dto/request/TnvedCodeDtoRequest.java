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
    @NotNull(message = "Code is mandatory")
    @NotBlank(message = "Code is mandatory")
    private String code;
    @NotNull(message = "Description is mandatory")
    @NotBlank(message = "Description is mandatory")
    private String description;
    @NotNull(message = "Default customs duty rate is mandatory")
    @NotBlank(message = "Default customs duty rate is mandatory")
    private BigDecimal defaultCustomsDutyRate;
    @NotNull(message = "Default excise rate is mandatory")
    @NotBlank(message = "Default excise rate is mandatory")
    private BigDecimal defaultExciseRate;
    @NotNull(message = "Default nds rate is mandatory")
    @NotBlank(message = "Default nds rate is mandatory")
    private BigDecimal defaultNdsRate;
}
