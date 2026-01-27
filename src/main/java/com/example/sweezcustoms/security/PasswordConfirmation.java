package com.example.sweezcustoms.security;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordConfirmation {
    @NotNull(message = "{auth.password.notnull}")
    @NotEmpty(message = "auth.password.notempty")
    private String newPassword;
    @NotNull(message = "{auth.password.notnull}")
    @NotEmpty(message = "{auth.password.notempty}")
    private String confirmPassword;
}
