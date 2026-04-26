package com.example.sweezcustoms.dto.request;

import com.example.sweezcustoms.enums.DeclarationTypeEnum;
import com.example.sweezcustoms.enums.FuelTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Schema(description = "Данные для создания декларации на автомобиль")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDeclarationDtoRequest {

    @Schema(description = "Тип декларации", example = "IMPORT", allowableValues = {"IMPORT", "EXPORT"})
    @NotNull(message = "{declaration.type.required}")
    private DeclarationTypeEnum declarationType;

    @Schema(description = "Валюта", example = "USD")
    private String currency;

    @Schema(description = "VIN-код автомобиля (17 символов)", example = "1HGBH41JXMN109186")
    @NotBlank(message = "{car.vin.required}")
    private String vinCode;

    @Schema(description = "Марка автомобиля", example = "Toyota")
    @NotBlank(message = "{car.brand.required}")
    private String brand;

    @Schema(description = "Модель автомобиля", example = "Camry")
    @NotBlank(message = "{car.model.required}")
    private String model;

    @Schema(description = "Цвет", example = "Белый")
    @NotBlank(message = "{car.color.required}")
    private String color;

    @Schema(description = "Год выпуска", example = "2020")
    @NotNull(message = "{car.year.required}")
    private Integer manufactureYear;

    @Schema(description = "Объём двигателя (куб.см), null для электромобилей", example = "2500")
    private Integer engineVolume;

    @Schema(description = "Мощность двигателя (л.с.), null для электромобилей", example = "180")
    private Integer enginePowerHp;

    @Schema(description = "Мощность двигателя (кВт), обязательно для электромобилей", example = "150")
    private Integer enginePowerKw;

    @Schema(description = "Тип топлива", example = "PETROL",
            allowableValues = {"PETROL", "DIESEL", "ELECTRIC", "HYBRID", "GAS", "HYDROGEN"})
    @NotNull(message = "{car.fuel.required}")
    private FuelTypeEnum fuelType;

    @Schema(description = "Номер технического паспорта", example = "KG123456789")
    @NotBlank(message = "{car.title.required}")
    private String titleNumber;

    @Schema(description = "Стоимость автомобиля в валюте декларации", example = "25000.00")
    private java.math.BigDecimal carValue;

    @Schema(description = "Пробег (км)", example = "45000")
    private Integer mileage;
}
