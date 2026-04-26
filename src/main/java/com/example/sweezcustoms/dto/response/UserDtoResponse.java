package com.example.sweezcustoms.dto.response;

import com.example.sweezcustoms.dto.view.CompanyDtoView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Полные данные пользователя")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoResponse {
    @Schema(description = "ID пользователя", example = "1")
    private Long id;
    @Schema(description = "Email", example = "user@example.com")
    private String mail;
    @Schema(description = "Полное имя", example = "Иванов Иван")
    private String fullName;
    @Schema(description = "Номер телефона", example = "+996700123456")
    private String phone;
    @Schema(description = "Роли пользователя", example = "[\"COMPANY_USER\"]")
    private List<String> roles;
    @Schema(description = "Дата регистрации", example = "2024-01-15")
    private LocalDate createdAt;
    @Schema(description = "Компания пользователя")
    private CompanyDtoView companyDtoView;
}
