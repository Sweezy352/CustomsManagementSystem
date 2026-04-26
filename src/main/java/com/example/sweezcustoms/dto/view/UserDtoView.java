package com.example.sweezcustoms.dto.view;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema(description = "Краткие данные пользователя")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoView {
    @Schema(description = "ID", example = "1")
    private Long id;
    @Schema(description = "Email", example = "user@example.com")
    private String mail;
    @Schema(description = "Полное имя", example = "Иванов Иван")
    private String fullName;
    @Schema(description = "Номер телефона", example = "+996700123456")
    private String phone;
    @Schema(description = "Роли", example = "[\"COMPANY_USER\"]")
    private List<String> roles;
}
