package com.example.sweezcustoms.security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Данные для входа в систему")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationTokenRequest {
    @Schema(description = "Email пользователя", example = "user@example.com")
    @NotNull(message = "{error.auth}")
    @NotEmpty(message = "{error.auth}")
    @NotBlank(message = "{error.auth}")
    private String email;
    @Schema(description = "Пароль", example = "secret123")
    @NotNull(message = "{error.auth}")
    @NotEmpty(message = "{error.auth}")
    @NotBlank(message = "{error.auth}")
    private String password;
}
