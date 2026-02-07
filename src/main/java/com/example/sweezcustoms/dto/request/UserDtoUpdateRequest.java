package com.example.sweezcustoms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDtoUpdateRequest {
    @NotNull(message = "{mail.required}")
    @NotBlank(message = "{mail.required}")
    private String email;
    @NotNull(message = "{phone.required}")
    @NotBlank(message = "{phone.required}")
    private String phone;
}
