package com.example.sweezcustoms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Schema(description = "Данные для регистрации компании")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDtoRequest {
    @Schema(description = "Название компании (5-30 символов)", example = "Bishkek Style LLC")
    @NotNull(message = "{company.name.required}")
    @NotBlank(message = "{company.name.required}")
    @Size(min = 5, max = 30, message = "{company.name.size}")
    private String name;
    @Schema(description = "Юридический адрес компании", example = "Бишкек, ул. Молодая Гвардия, 1")
    @NotNull(message = "{address.required}")
    @NotBlank(message = "{address.required}")
    private String address;
}
