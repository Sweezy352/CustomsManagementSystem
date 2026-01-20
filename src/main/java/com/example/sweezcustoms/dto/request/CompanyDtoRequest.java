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
    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name is mandatory")
    @Size(min = 5, max = 30, message = "Name must be between 5 and 30 characters")
    private String name;
    @NotNull(message = "Tin number is mandatory")
    @NotBlank(message = "Tin number is mandatory")
    private String tin;
    @NotNull(message = "Address is mandatory")
    @NotBlank(message = "Address is mandatory")
    private String address;
}
