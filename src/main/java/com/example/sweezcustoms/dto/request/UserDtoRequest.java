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
    @NotNull(message = "Username is mandatory")
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotNull(message = "Mail is mandatory")
    @NotBlank(message = "Mail is mandatory")
    private String mail;
    @NotNull(message = "Phone is mandatory")
    @NotBlank(message = "Phone is mandatory")
    private String phone;
    @NotNull(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 5, max = 15, message = "Password must be between 5 and 15 characters")
    private String password;
    @NotNull(message = "Role is mandatory")
    @NotBlank(message = "Role is mandatory")
    private String roleName;
}
