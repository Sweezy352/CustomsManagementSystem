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
    @NotNull(message = "{individual.fullname.required}")
    @NotBlank(message = "{individual.fullname.required}")
    private String fullName;
    @NotNull(message = "{individual.passport.required}")
    @NotBlank(message = "{individual.passport.required}")
    private String passportSeries;
    @NotNull(message = "{individual.birthdate.required}")
    @NotBlank(message = "{individual.birthdate.required}")
    private LocalDate birthDate;
    @NotNull(message = "{address.required}")
    @NotBlank(message = "{address.required}")
    private String address;
    @NotNull(message = "{tin.required}")
    @NotBlank(message = "{tin.required}")
    private String tin;
}
