package com.example.sweezcustoms.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Пара JWT-токенов")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationToken {
    @Schema(description = "Access-токен", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String authenticationToken;
    @Schema(description = "Refresh-токен", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String refreshToken;
}
