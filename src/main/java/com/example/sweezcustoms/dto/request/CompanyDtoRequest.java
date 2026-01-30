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
public class CompanyDtoRequest {
    @NotNull(message = "{company.name.required}")
    @NotBlank(message = "{company.name.required}")
    @Size(min = 5, max = 30, message = "{company.name.size}")
    private String name;
    @NotNull(message = "{address.required}")
    @NotBlank(message = "{address.required}")
    private String address;
}
