package com.example.sweezcustoms.dto.response;

import com.example.sweezcustoms.enums.CustomsStatusEnum;
import com.example.sweezcustoms.enums.FuelTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Данные декларации на автомобиль")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDeclarationDtoResponse {

    @Schema(description = "ID декларации", example = "1")
    private Long id;

    @Schema(description = "Тип декларации", example = "IMPORT")
    private String declarationType;

    @Schema(description = "Статус", example = "DRAFT")
    private CustomsStatusEnum status;

    @Schema(description = "Валюта", example = "USD")
    private String currency;

    @Schema(description = "Курс валюты", example = "87.50")
    private BigDecimal currencyRate;

    @Schema(description = "Дата создания")
    private LocalDateTime createdAt;

    @Schema(description = "Дата подачи")
    private LocalDateTime submittedAt;

    @Schema(description = "VIN-код", example = "1HGBH41JXMN109186")
    private String vinCode;

    @Schema(description = "Марка", example = "Toyota")
    private String brand;

    @Schema(description = "Модель", example = "Camry")
    private String model;

    @Schema(description = "Цвет", example = "Белый")
    private String color;

    @Schema(description = "Год выпуска", example = "2020")
    private Integer manufactureYear;

    @Schema(description = "Объём двигателя (куб.см)", example = "2500")
    private Integer engineVolume;

    @Schema(description = "Мощность (л.с.)", example = "180")
    private Integer enginePowerHp;

    @Schema(description = "Мощность (кВт)", example = "150")
    private Integer enginePowerKw;

    @Schema(description = "Тип топлива", example = "PETROL")
    private FuelTypeEnum fuelType;

    @Schema(description = "Номер тех.паспорта", example = "KG123456789")
    private String titleNumber;

    @Schema(description = "Стоимость автомобиля", example = "25000.00")
    private java.math.BigDecimal carValue;

    @Schema(description = "Пробег (км)", example = "45000")
    private Integer mileage;

    @Schema(description = "Рассчитанная таможенная пошлина", example = "1250.00")
    private BigDecimal customsDuty;

    @Schema(description = "Рассчитанный НДС", example = "3000.00")
    private BigDecimal nds;

    @Schema(description = "Рассчитанный акциз", example = "500.00")
    private BigDecimal excise;
}
