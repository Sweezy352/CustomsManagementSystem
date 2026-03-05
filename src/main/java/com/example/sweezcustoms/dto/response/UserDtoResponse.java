package com.example.sweezcustoms.dto.response;

import com.example.sweezcustoms.dto.view.CompanyDtoView;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoResponse {
    private Long id;
    private String mail;
    private String fullName;
    private String phone;
    private List<String> roles;
    private LocalDate createdAt;
    private CompanyDtoView companyDtoView;
}
