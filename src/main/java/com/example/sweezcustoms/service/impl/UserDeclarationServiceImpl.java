package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.entity.PaymentInvoiceEntity;
import com.example.sweezcustoms.entity.UserDeclaration;
import com.example.sweezcustoms.entity.UserEntity;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import com.example.sweezcustoms.exceptions.BaseException;
import com.example.sweezcustoms.exceptions.DeclarationNotFoundException;
import com.example.sweezcustoms.exceptions.ErrorBody;
import com.example.sweezcustoms.repository.PaymentInvoiceRepository;
import com.example.sweezcustoms.repository.UserDeclarationRepository;
import com.example.sweezcustoms.service.AuthService;
import com.example.sweezcustoms.service.UserDeclarationService;
import com.example.sweezcustoms.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDeclarationServiceImpl implements UserDeclarationService {
    private final UserDeclarationRepository userDeclarationRepository;
    private final AuthService authService;
    private final UserService userService;
    private final PaymentInvoiceRepository paymentInvoiceRepository;

    @Override
    public UserDeclaration createDeclaration(UserDeclaration userDeclaration) {
        return userDeclarationRepository.save(userDeclaration);
    }

    @Override
    public List<UserDeclaration> getAllDeclarations() {
        UserEntity currentUser = authService.getCurrent();
        return currentUser.getUserDeclarations();
    }

    @Override
    public List<UserDeclaration> getFullUserDeclarations(Long userId) {
        UserEntity user = userService.getById(userId);
        return user.getUserDeclarations();
    }

    @Override
    public UserDeclaration getDeclarationById(Long id) {
        return userDeclarationRepository.findById(id).orElseThrow(() -> new DeclarationNotFoundException("Declaration not found"));
    }

    @Override
    public UserDeclaration updateDeclaration(UserDeclaration userDeclaration) {
        return userDeclarationRepository.save(userDeclaration);
    }

    @Override
    @Transactional
    public UserDeclaration submitDeclaration(Long id) {
        UserDeclaration declaration = getDeclarationById(id);
        if (declaration.getStatus() != CustomsStatusEnum.DRAFT) {
            throw new BaseException(ErrorBody.builder().code(400).message("declaration.not.in.draft").build());
        }
        declaration.setStatus(CustomsStatusEnum.SUBMITTED);
        declaration.setSubmittedAt(LocalDateTime.now());
        UserDeclaration saved = userDeclarationRepository.save(declaration);
        generatePaymentInvoice(saved);
        return saved;
    }

    @Override
    public UserDeclaration approveDeclaration(Long id) {
        UserDeclaration declaration = getDeclarationById(id);
        if (declaration.getStatus() != CustomsStatusEnum.SUBMITTED) {
            throw new BaseException(ErrorBody.builder().code(400).message("declaration.not.submitted").build());
        }
        declaration.setStatus(CustomsStatusEnum.APPROVED);
        return userDeclarationRepository.save(declaration);
    }

    @Override
    public UserDeclaration rejectDeclaration(Long id, String reason) {
        UserDeclaration declaration = getDeclarationById(id);
        if (declaration.getStatus() != CustomsStatusEnum.SUBMITTED) {
            throw new BaseException(ErrorBody.builder().code(400).message("declaration.not.submitted").build());
        }
        declaration.setStatus(CustomsStatusEnum.REJECTED);
        return userDeclarationRepository.save(declaration);
    }

    private void generatePaymentInvoice(UserDeclaration declaration) {
        BigDecimal totalNds = BigDecimal.ZERO;
        BigDecimal totalDuty = BigDecimal.ZERO;
        BigDecimal totalExcise = BigDecimal.ZERO;

        if (declaration.getDeclarationProducts() != null) {
            for (var product : declaration.getDeclarationProducts()) {
                totalNds = totalNds.add(product.getNds() != null ? product.getNds() : BigDecimal.ZERO);
                totalDuty = totalDuty.add(product.getCustomsDuty() != null ? product.getCustomsDuty() : BigDecimal.ZERO);
                totalExcise = totalExcise.add(product.getExcise() != null ? product.getExcise() : BigDecimal.ZERO);
            }
        }

        PaymentInvoiceEntity invoice = PaymentInvoiceEntity.builder()
                .declarationEntity(declaration)
                .totalInvoiceNds(totalNds)
                .totalInvoiceCustomsDuty(totalDuty)
                .totalInvoiceExcise(totalExcise)
                .invoiceTotal(totalNds.add(totalDuty).add(totalExcise))
                .build();
        paymentInvoiceRepository.save(invoice);
    }
}
