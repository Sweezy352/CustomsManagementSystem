package com.example.sweezcustoms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndividualDtoRequest {
    @NotNull(message = "Full name is mandatory")
    @NotBlank(message = "Full name is mandatory")
    private String fullName;
    @NotNull(message = "Passport series is mandatory")
    @NotBlank(message = "Passport series is mandatory")
    private String passportSeries;
    @NotNull(message = "Birth date is mandatory")
    @NotBlank(message = "Birth date is mandatory")
    private LocalDate birthDate;
    @NotNull(message = "Address is mandatory")
    @NotBlank(message = "Address is mandatory")
    private String address;
    @NotNull(message = "Tin is mandatory")
    @NotBlank(message = "Tin is mandatory")
    private String tin;
}
