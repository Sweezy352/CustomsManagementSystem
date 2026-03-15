package com.example.sweezcustoms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRequest {
    @NotBlank(message = "{mail.required}")
    private String mail;
    @NotBlank(message = "{full_name.required}")
    private String fullName;
    @NotBlank(message = "{}")
    @Size(min = 14, max = 14, message = "${}")
    private String pin;
    @NotNull(message = "{}")
    @NotBlank(message = "{}")
    private String passportNumber;
    @NotNull(message = "{}")
    private LocalDate birthDate;
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
