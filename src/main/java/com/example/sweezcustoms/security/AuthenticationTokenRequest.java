package com.example.sweezcustoms.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationTokenRequest {
    @NotNull(message = "{error.auth}")
    @NotEmpty(message = "{error.auth}")
    @NotBlank(message = "{error.auth}")
    private String email;
    @NotNull(message = "{error.auth}")
    @NotEmpty(message = "{error.auth}")
    @NotBlank(message = "{error.auth}")
    private String password;
}
