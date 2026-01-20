package com.example.sweezcustoms.dto.response;

import com.example.sweezcustoms.dto.view.UserDtoView;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TnvedCodeDtoResponse {
    private Long id;
    private String code;
    private String description;
    private BigDecimal defaultCustomsDutyRate;
    private BigDecimal defaultExciseRate;
    private BigDecimal defaultNdsRate;
    private UserDtoView userDtoView;
    private LocalDateTime dateCreated;
}
