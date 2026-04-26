package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.entity.CarDeclaration;
import com.example.sweezcustoms.entity.PaymentInvoiceEntity;
import com.example.sweezcustoms.entity.UserEntity;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import com.example.sweezcustoms.enums.FuelTypeEnum;
import com.example.sweezcustoms.exceptions.BaseException;
import com.example.sweezcustoms.exceptions.CarDeclarationNotFoundException;
import com.example.sweezcustoms.exceptions.ErrorBody;
import com.example.sweezcustoms.repository.CarDeclarationRepository;
import com.example.sweezcustoms.repository.PaymentInvoiceRepository;
import com.example.sweezcustoms.service.AuthService;
import com.example.sweezcustoms.service.CarDeclarationService;
import com.example.sweezcustoms.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarDeclarationServiceImpl implements CarDeclarationService {

    private final CarDeclarationRepository carDeclarationRepository;
    private final PaymentInvoiceRepository paymentInvoiceRepository;
    private final AuthService authService;
    private final CompanyService companyService;

    // Ставка НДС — 12% (стандарт КР)
    private static final BigDecimal NDS_RATE = new BigDecimal("0.12");

    @Override
    public CarDeclaration createForUser(CarDeclaration carDeclaration) {
        UserEntity currentUser = authService.getCurrent();
        carDeclaration.setUserEntity(currentUser);
        calculateDuties(carDeclaration);
        return carDeclarationRepository.save(carDeclaration);
    }

    @Override
    public CarDeclaration createForCompany(Long companyId, CarDeclaration carDeclaration) {
        carDeclaration.setCompanyEntity(companyService.getCompanyById(companyId));
        calculateDuties(carDeclaration);
        return carDeclarationRepository.save(carDeclaration);
    }

    @Override
    public CarDeclaration getById(Long id) {
        return carDeclarationRepository.findById(id)
                .orElseThrow(() -> new CarDeclarationNotFoundException("car.declaration.not.found"));
    }

    @Override
    public CarDeclaration getByVin(String vinCode) {
        return carDeclarationRepository.findByVinCode(vinCode)
                .orElseThrow(() -> new CarDeclarationNotFoundException("car.declaration.not.found"));
    }

    @Override
    public List<CarDeclaration> getAllByUser() {
        return carDeclarationRepository.findAllByUserEntityId(authService.getCurrent().getId());
    }

    @Override
    public List<CarDeclaration> getAllByCompany(Long companyId) {
        return carDeclarationRepository.findAllByCompanyEntityId(companyId);
    }

    @Override
    public CarDeclaration submitDeclaration(Long id) {
        CarDeclaration declaration = getById(id);
        if (declaration.getStatus() != CustomsStatusEnum.DRAFT) {
            throw new BaseException(ErrorBody.builder().code(400).message("declaration.not.in.draft").build());
        }
        declaration.setStatus(CustomsStatusEnum.SUBMITTED);
        declaration.setSubmittedAt(LocalDateTime.now());
        CarDeclaration saved = carDeclarationRepository.save(declaration);
        generatePaymentInvoice(saved);
        return saved;
    }

    @Override
    public CarDeclaration approveDeclaration(Long id) {
        CarDeclaration declaration = getById(id);
        if (declaration.getStatus() != CustomsStatusEnum.SUBMITTED) {
            throw new BaseException(ErrorBody.builder().code(400).message("declaration.not.submitted").build());
        }
        declaration.setStatus(CustomsStatusEnum.APPROVED);
        return carDeclarationRepository.save(declaration);
    }

    @Override
    public CarDeclaration rejectDeclaration(Long id, String reason) {
        CarDeclaration declaration = getById(id);
        if (declaration.getStatus() != CustomsStatusEnum.SUBMITTED) {
            throw new BaseException(ErrorBody.builder().code(400).message("declaration.not.submitted").build());
        }
        declaration.setStatus(CustomsStatusEnum.REJECTED);
        return carDeclarationRepository.save(declaration);
    }

    /**
     * Расчёт таможенных платежей для автомобиля.
     *
     * Логика (упрощённая, по стандарту ЕАЭС):
     * - Электромобили: пошлина 0%, акциз 0%, НДС 12% от стоимости
     * - Бензин/дизель до 3 лет: пошлина 15% от стоимости, акциз от объёма двигателя
     * - Бензин/дизель старше 3 лет: пошлина 20% от стоимости, акциз повышенный
     * - НДС всегда 12% от (стоимость + пошлина + акциз)
     */
    private void calculateDuties(CarDeclaration car) {
        if (car.getCarValue() == null) return;

        BigDecimal carValue = car.getCarValue();
        int age = LocalDateTime.now().getYear() - (car.getManufactureYear() != null ? car.getManufactureYear() : 0);

        BigDecimal customsDuty;
        BigDecimal excise;

        if (car.getFuelType() == FuelTypeEnum.ELECTRIC) {
            // Электромобили — льготная ставка
            customsDuty = BigDecimal.ZERO;
            excise = BigDecimal.ZERO;
        } else {
            // Пошлина: до 3 лет — 15%, старше — 20%
            BigDecimal dutyRate = age <= 3 ? new BigDecimal("0.15") : new BigDecimal("0.20");
            customsDuty = carValue.multiply(dutyRate).setScale(2, RoundingMode.HALF_UP);

            // Акциз от объёма двигателя (сом за куб.см)
            // до 3 лет: 0.5 сом/куб.см, старше 3 лет: 1.0 сом/куб.см
            if (car.getEngineVolume() != null && car.getEngineVolume() > 0) {
                BigDecimal excisePerCc = age <= 3 ? new BigDecimal("0.5") : new BigDecimal("1.0");
                excise = excisePerCc.multiply(BigDecimal.valueOf(car.getEngineVolume()))
                        .setScale(2, RoundingMode.HALF_UP);
            } else {
                excise = BigDecimal.ZERO;
            }
        }

        // НДС = 12% от (стоимость + пошлина + акциз)
        BigDecimal taxBase = carValue.add(customsDuty).add(excise);
        BigDecimal nds = taxBase.multiply(NDS_RATE).setScale(2, RoundingMode.HALF_UP);

        // Сохраняем в поля DeclarationProduct-подобной логики через DeclarationEntity
        // Используем currencyRate как базу стоимости, результаты пишем в declarationProducts[0]
        // Для авто создаём один "виртуальный" продукт через поля сущности
        car.setNdsAmount(nds);
        car.setCustomsDutyAmount(customsDuty);
        car.setExciseAmount(excise);
    }

    private void generatePaymentInvoice(CarDeclaration declaration) {
        BigDecimal nds = declaration.getNdsAmount() != null ? declaration.getNdsAmount() : BigDecimal.ZERO;
        BigDecimal duty = declaration.getCustomsDutyAmount() != null ? declaration.getCustomsDutyAmount() : BigDecimal.ZERO;
        BigDecimal excise = declaration.getExciseAmount() != null ? declaration.getExciseAmount() : BigDecimal.ZERO;

        PaymentInvoiceEntity invoice = PaymentInvoiceEntity.builder()
                .declarationEntity(declaration)
                .totalInvoiceNds(nds)
                .totalInvoiceCustomsDuty(duty)
                .totalInvoiceExcise(excise)
                .invoiceTotal(nds.add(duty).add(excise))
                .build();
        paymentInvoiceRepository.save(invoice);
    }
}
