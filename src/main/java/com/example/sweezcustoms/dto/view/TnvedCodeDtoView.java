package com.example.sweezcustoms.dto.view;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TnvedCodeDtoView {
    private Long id;
    private String code;
    private String description;
}
