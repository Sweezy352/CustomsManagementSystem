package com.example.sweezcustoms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Schema(description = "Данные для регистрации нового пользователя")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRequest {
    @Schema(description = "Email пользователя", example = "user@example.com")
    @NotBlank(message = "{mail.required}")
    private String mail;
    @Schema(description = "Полное имя", example = "Иванов Иван Иванович")
    @NotBlank(message = "{validation.firstname.required}")
    private String fullName;
    @Schema(description = "ПИН (14 цифр)", example = "12345678901234")
    @NotBlank(message = "{}")
    @Size(min = 14, max = 14, message = "${}")
    private String pin;
    @Schema(description = "Номер паспорта", example = "AN1234567")
    @NotNull(message = "{}")
    @NotBlank(message = "{}")
    private String passportNumber;
    @Schema(description = "Дата рождения", example = "1990-01-15")
    @NotNull(message = "{}")
    private LocalDate birthDate;
    @Schema(description = "Номер телефона", example = "+996700123456")
    @NotNull(message = "{phone.required}")
    @NotBlank(message = "{phone.required}")
    private String phone;
    @Schema(description = "Пароль (5-15 символов)", example = "secret123")
    @NotNull(message = "{auth.password.notnull}")
    @NotBlank(message = "{auth.password.notnull}")
    @Size(min = 5, max = 15, message = "{auth.password.size}")
    private String password;
    @Schema(description = "Роль пользователя", example = "COMPANY_USER", allowableValues = {"ADMIN", "INSPECTOR", "COMPANY_USER", "INDIVIDUAL_USER"})
    @NotNull(message = "{role.required}")
    @NotBlank(message = "{role.required}")
    private String roleName;
}
