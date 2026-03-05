package com.example.sweezcustoms.dto.response;

import com.example.sweezcustoms.dto.view.UserDtoView;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import com.example.sweezcustoms.enums.IndividualDocumentType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndividualDocumentDtoResponse {
    private Long id;
    private UserDtoView userDtoView;
    private IndividualDocumentType individualDocumentType;
    private Long fileId;
    private LocalDateTime uploadedAt;
    private CustomsStatusEnum status;
    private UserDtoView verifiedBy;
    private LocalDateTime verifiedAt;
}
