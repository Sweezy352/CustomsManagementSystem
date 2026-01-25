package com.example.sweezcustoms.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
    @NotNull(message = "{auth.username.notnull}")
    @NotEmpty(message = "{auth.username.notempty}")
    private String username;
    @NotNull(message = "{auth.password.notnull}")
    @NotEmpty(message = "{auth.password.notempty}")
    private String password;
}
