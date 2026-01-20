package com.example.sweezcustoms.dto.response;

import com.example.sweezcustoms.dto.view.DeclarationProductDtoView;
import com.example.sweezcustoms.dto.view.UserDtoView;
import com.example.sweezcustoms.dto.view.ParticipantDto;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import com.example.sweezcustoms.enums.DeclarationTypeEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeclarationDtoResponse{
    private Long id;
    private ParticipantDto participantDto;
    private DeclarationTypeEnum type;
    private CustomsStatusEnum status;
    private LocalDateTime createdAt;
    private LocalDateTime verifiedAt;
    private UserDtoView verifiedBy;
    private List<DeclarationProductDtoView> declarationProductDtoViews;
}
