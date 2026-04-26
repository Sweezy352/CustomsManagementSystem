package com.example.sweezcustoms.entity;

import com.example.sweezcustoms.enums.FuelTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "car_declarations")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CarDeclaration extends DeclarationEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private CompanyEntity companyEntity;

    @Column(name = "vin_code", nullable = false, unique = true, length = 20)
    private String vinCode;

    @Column(name = "brand", nullable = false, length = 50)
    private String brand;

    @Column(name = "model", nullable = false, length = 50)
    private String model;

    @Column(name = "color", nullable = false, length = 20)
    private String color;

    @Column(name = "manufacture_year", nullable = false)
    private Integer manufactureYear;

    // null для электромобилей
    @Column(name = "engine_volume")
    private Integer engineVolume;

    // null для электромобилей
    @Column(name = "engine_power_hp")
    private Integer enginePowerHp;

    // Важно для электромобилей
    @Column(name = "engine_power_kw")
    private Integer enginePowerKw;

    @Column(name = "fuel_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private FuelTypeEnum fuelType;

    // Технический паспорт
    @Column(name = "title_number", nullable = false, length = 20)
    private String titleNumber;

    @Column(name = "mileage")
    private Integer mileage;

    // Стоимость автомобиля в валюте декларации
    @Column(name = "car_value")
    private java.math.BigDecimal carValue;

    // Рассчитанные таможенные платежи
    @Column(name = "nds_amount")
    private java.math.BigDecimal ndsAmount;

    @Column(name = "customs_duty_amount")
    private java.math.BigDecimal customsDutyAmount;

    @Column(name = "excise_amount")
    private java.math.BigDecimal exciseAmount;
}
