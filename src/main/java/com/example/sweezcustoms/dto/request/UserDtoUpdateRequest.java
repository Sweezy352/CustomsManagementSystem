package com.example.sweezcustoms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Schema(description = "Данные для обновления профиля пользователя")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDtoUpdateRequest {
    @Schema(description = "Email пользователя", example = "new@example.com")
    @NotNull(message = "{mail.required}")
    @NotBlank(message = "{mail.required}")
    private String email;
    @Schema(description = "Новый номер телефона", example = "+996700999888")
    @NotNull(message = "{phone.required}")
    @NotBlank(message = "{phone.required}")
    private String phone;
}
