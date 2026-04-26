package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.entity.CompanyDeclaration;
import com.example.sweezcustoms.entity.PaymentInvoiceEntity;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import com.example.sweezcustoms.exceptions.BaseException;
import com.example.sweezcustoms.exceptions.DeclarationNotFoundException;
import com.example.sweezcustoms.exceptions.ErrorBody;
import com.example.sweezcustoms.repository.CompanyDeclarationRepository;
import com.example.sweezcustoms.repository.PaymentInvoiceRepository;
import com.example.sweezcustoms.service.CompanyDeclarationService;
import com.example.sweezcustoms.service.CompanyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyDeclarationServiceImpl implements CompanyDeclarationService {
    private final CompanyDeclarationRepository companyDeclarationRepository;
    private final CompanyService companyService;
    private final PaymentInvoiceRepository paymentInvoiceRepository;

    @Override
    public CompanyDeclaration createDeclaration(CompanyDeclaration companyDeclaration) {
        return companyDeclarationRepository.save(companyDeclaration);
    }

    @Override
    public List<CompanyDeclaration> getAllDeclarations() {
        return companyDeclarationRepository.findAll();
    }

    @Override
    public List<CompanyDeclaration> getFullCompanyDeclarations(Long companyId) {
        return companyService.getCompanyById(companyId).getCompanyDeclarations();
    }

    @Override
    public CompanyDeclaration getDeclarationById(Long id) {
        return companyDeclarationRepository.findById(id).orElseThrow(() -> new DeclarationNotFoundException("declaration.not.found"));
    }

    @Override
    public CompanyDeclaration updateDeclaration(Long declarationId, CompanyDeclaration companyDeclaration) {
        companyDeclaration.setId(declarationId);
        return companyDeclarationRepository.save(companyDeclaration);
    }

    @Override
    @Transactional
    public CompanyDeclaration submitDeclaration(Long id) {
        CompanyDeclaration declaration = getDeclarationById(id);
        if (declaration.getStatus() != CustomsStatusEnum.DRAFT) {
            throw new BaseException(ErrorBody.builder().code(400).message("declaration.not.in.draft").build());
        }
        declaration.setStatus(CustomsStatusEnum.SUBMITTED);
        declaration.setSubmittedAt(LocalDateTime.now());
        CompanyDeclaration saved = companyDeclarationRepository.save(declaration);
        generatePaymentInvoice(saved);
        return saved;
    }

    @Override
    public CompanyDeclaration approveDeclaration(Long id) {
        CompanyDeclaration declaration = getDeclarationById(id);
        if (declaration.getStatus() != CustomsStatusEnum.SUBMITTED) {
            throw new BaseException(ErrorBody.builder().code(400).message("declaration.not.submitted").build());
        }
        declaration.setStatus(CustomsStatusEnum.APPROVED);
        return companyDeclarationRepository.save(declaration);
    }

    @Override
    public CompanyDeclaration rejectDeclaration(Long id, String reason) {
        CompanyDeclaration declaration = getDeclarationById(id);
        if (declaration.getStatus() != CustomsStatusEnum.SUBMITTED) {
            throw new BaseException(ErrorBody.builder().code(400).message("declaration.not.submitted").build());
        }
        declaration.setStatus(CustomsStatusEnum.REJECTED);
        return companyDeclarationRepository.save(declaration);
    }

    private void generatePaymentInvoice(CompanyDeclaration declaration) {
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
