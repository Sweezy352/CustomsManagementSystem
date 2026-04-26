package com.example.sweezcustoms.security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Schema(description = "Данные для сброса пароля")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordConfirmation {
    @Schema(description = "Новый пароль", example = "newSecret123")
    @NotNull(message = "{auth.password.notnull}")
    @NotEmpty(message = "auth.password.notempty")
    private String newPassword;
    @Schema(description = "Подтверждение нового пароля", example = "newSecret123")
    @NotNull(message = "{auth.password.notnull}")
    @NotEmpty(message = "{auth.password.notempty}")
    private String confirmPassword;
}
