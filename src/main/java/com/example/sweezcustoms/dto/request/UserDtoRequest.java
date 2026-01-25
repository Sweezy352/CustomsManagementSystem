package com.example.sweezcustoms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRequest {
    @NotNull(message = "{auth.username.notnull}")
    @NotBlank(message = "{auth.username.notnull}")
    private String username;
    @NotNull(message = "{mail.required}")
    @NotBlank(message = "{mail.required}")
    private String mail;
    @NotNull(message = "{phone.required}")
    @NotBlank(message = "{phone.required}")
    private String phone;
    @NotNull(message = "{auth.password.notnull}")
    @NotBlank(message = "{auth.password.notnull}")
    @Size(min = 5, max = 15, message = "{auth.password.size}")
    private String password;
    @NotNull(message = "{role.required}")
    @NotBlank(message = "{role.required}")
    private String roleName;
}
