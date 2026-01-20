package com.example.sweezcustoms.dto.response;

import com.example.sweezcustoms.dto.view.DeclarationDtoView;
import com.example.sweezcustoms.dto.view.TnvedCodeDtoView;
import com.example.sweezcustoms.dto.view.UserDtoView;
import com.example.sweezcustoms.entity.TnvedCodeEntity;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import com.example.sweezcustoms.enums.DeclarationTypeEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationProductDtoResponse {
    private Long id;
    private DeclarationDtoView declarationDtoView;
    private String name;
    private TnvedCodeDtoView tnvedCode;
    private Long quantity;
    private BigDecimal weight;
    private BigDecimal pricePerUnit;
    private BigDecimal defaultNdsRate;
    private BigDecimal defaultCustomsDutyRate;
    private BigDecimal defaultExciseRate;
    private String countryOfOrigin;
    private CustomsStatusEnum status;
    private LocalDateTime createdAt;
    private UserDtoView verifiedBy;
    private LocalDateTime verifiedAt;
}
